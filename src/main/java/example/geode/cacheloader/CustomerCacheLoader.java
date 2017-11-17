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

import org.apache.geode.cache.CacheLoader;
import org.apache.geode.cache.CacheLoaderException;
import org.apache.geode.cache.Declarable;
import org.apache.geode.cache.LoaderHelper;
import org.hibernate.Session;

public class CustomerCacheLoader implements CacheLoader<Integer, Customer>, Declarable {

    @Override
    public Customer load(LoaderHelper<Integer, Customer> helper) throws CacheLoaderException {
        Customer returnValue;

        Session session = HibernateSingleton.getInstance().openSession();
        try {
            returnValue = session.get(Customer.class, helper.getKey());
        } finally {
            session.close();
        }
        return returnValue;
    }

    @Override
    public void close() {
    }
}
