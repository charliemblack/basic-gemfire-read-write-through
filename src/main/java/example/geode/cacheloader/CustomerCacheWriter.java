/*
 * Copyright  2017 Charlie Black
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package example.geode.cacheloader;

import org.apache.geode.cache.CacheWriterException;
import org.apache.geode.cache.Declarable;
import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.util.CacheWriterAdapter;
import org.apache.geode.pdx.PdxInstance;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CustomerCacheWriter extends CacheWriterAdapter implements Declarable {
    @Override
    public void beforeCreate(EntryEvent event) throws CacheWriterException {
        Customer customer = safeGetCustomer(event.getNewValue());
        if(customer != null){
            Session session = HibernateSingleton.getInstance().openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.saveOrUpdate(customer);
                tx.commit();
            } catch (HibernateException e) {
               if(tx != null){
                   tx.rollback();
               }
            } finally {
                session.close();
            }
        }else{
            //todo throw some cool exception
        }
    }

    @Override
    public void beforeDestroy(EntryEvent event) throws CacheWriterException {
        Customer customer = safeGetCustomer(event.getOldValue());
        if(customer != null){
            Session session = HibernateSingleton.getInstance().openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.delete(customer);
                tx.commit();
            } catch (HibernateException e) {
                if(tx != null){
                    tx.rollback();
                }
            } finally {
                session.close();
            }
        }else{
            //todo throw some cool exception
        }
    }

    @Override
    public void beforeUpdate(EntryEvent event) throws CacheWriterException {
        beforeCreate(event);
    }
    private Customer safeGetCustomer(Object value){
        Customer customer = null;
        if(value instanceof PdxInstance){
            value = ((PdxInstance)value).getObject();
        }
        if(value instanceof Customer){
            customer = (Customer) value;
        }
        return customer;
    }
}
