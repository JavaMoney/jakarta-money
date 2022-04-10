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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class FieldMetadataTest {

    @Test
    public void shouldReturnIterable() {
        Inventory inventory = new Inventory("my inventory");
        inventory.add(new Product("Banana", "A fruit", 5));
        EntityMetadata metadata = EntityMetadata.of(Inventory.class);
        List<FieldMetadata> fields = metadata.getFields();
        FieldMetadata field = fields.get(0);
        Object read = field.read(inventory);
        Assertions.assertEquals(inventory.getProducts(), read);
    }

    @Test
    public void shouldReturnMap() {
        Map<String, String> contacts = new HashMap<>();
        contacts.put("Otavio", "123 456789");
        contacts.put("Poliana", "723 456789");
        Contact contact = new Contact(LocalDate.now(), "Ada", contacts);
        EntityMetadata metadata = EntityMetadata.of(Contact.class);
        List<FieldMetadata> fields = metadata.getFields();
        FieldMetadata field = fields.get(0);
        Object read = field.read(contact);
        Assertions.assertEquals(contacts, read);
    }

    @Test
    public void shouldReturnBoth() {
        String user = "Otavio";
        Set<String> medias = Set.of("Twitter", "Instagram");
        Map<String, String> postsBySocialMedia = Map.of("otaviojava", "my post", "otavio", "my photo");
        MediaUser mediaUser = new MediaUser(user, medias, postsBySocialMedia);
        EntityMetadata metadata = EntityMetadata.of(MediaUser.class);
        List<FieldMetadata> fields = metadata.getFields();
        FieldMetadata fieldA = fields.get(0);
        FieldMetadata fieldB = fields.get(1);

        Assertions.assertEquals(medias, fieldA.read(mediaUser));
        Assertions.assertEquals(postsBySocialMedia, fieldB.read(mediaUser));
    }
}