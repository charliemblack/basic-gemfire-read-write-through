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

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;

import java.util.Map;
import java.util.UUID;

public class Driver {
    public static void main(String[] args) {
        ClientCache clientCache = new ClientCacheFactory()
                .addPoolLocator("localhost", 10334)
                .setPdxSerializer(new ReflectionBasedAutoSerializer("example.geode.cacheloader.*"))
                .setPdxReadSerialized(false)
                .set("log-level", "warning")
                .create();
        Region<Integer, Customer> customerRegion = clientCache.<Integer, Customer>createClientRegionFactory(ClientRegionShortcut.PROXY).create("customers");

        // Calling the Cache Loader
        Customer customer = customerRegion.get(1);
        System.out.println("initial customer.getGuid() = " + customer.getGuid());

        customer.setGuid(UUID.randomUUID().toString());
        System.out.println("new customer.getGuid() = " + customer.getGuid());

        // Calling the cache writer update -> create
        customerRegion.put(1, customer);

        // This invalidate just removes the value from Geode so the next call to get would call the cache loader.
        customerRegion.invalidate(1);

        // Calling the Cache Loader
        customerRegion.get(1);
        System.out.println("after invalidating customer.getGuid() = " + customer.getGuid());

        System.out.println("Create a new customer");

        int newCustomerId = 100000;

        if(customerRegion.containsKeyOnServer(newCustomerId)){
            System.out.println("need to delete the customer before I create one");
            // Calling the Cache Writer before destroy
            customerRegion.remove(newCustomerId);
        }

        customer = customer.toBuilder()
                .id(newCustomerId)
                .guid(UUID.randomUUID().toString())
                .build();

        //Calling the cache writer before create
        customerRegion.put(customer.getId(), customer);

        customerRegion.get(newCustomerId);
        System.out.println("the new customer customer.getGuid() = " + customer.getGuid());
        System.out.println("customer = " + customer);



    }
}
