/*
 *    Copyright 2021 Otavio Santana
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package one.microstream.cdi.extension;

import java.time.LocalDate;
import java.util.Map;

public class Contact {

    private final LocalDate date;

    private final  String owner;

    private final  Map<String, String> contacts;

    public Contact(LocalDate date, String owner, Map<String, String> contacts) {
        this.date = date;
        this.owner = owner;
        this.contacts = contacts;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getOwner() {
        return owner;
    }

    public Map<String, String> getContacts() {
        return contacts;
    }
}
