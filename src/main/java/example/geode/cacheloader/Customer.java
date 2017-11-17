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


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity(name = "customer")
public class Customer {

    @Id
    private int id;
    private int telephonecountrycode, age;
    private String gender, nameset, title, givenname, middleinitial, surname, streetaddress, city, state, statefull,
            zipcode, country, countryfull, emailaddress, username, password, browseruseragent, telephonenumber,
            maidenname, birthday, tropicalzodiac, cctype, ccnumber, cvv2, ccexpires, nationalid, upstracking,
            color, occupation, company, vehicle, domain, bloodtype, pounds, kilograms,
            feetinches, centimeters, guid;
    @Column(name = "moneygrammtcn", columnDefinition = "bpchar(8)", length = 8)
    private String moneygrammtcn;
    @Column(name = "westernunionmtcn", columnDefinition = "bpchar(10)", length = 10)
    private String westernunionmtcn;
    private float latitude, longitude;
}