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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class EntityMetadataTest {

    @Test
    public void shouldReturnEmptyFields() {
        EntityMetadata metadata = EntityMetadata.of(Cat.class);
        Assertions.assertTrue(metadata.getFields().isEmpty());
    }

    @Test
    public void shouldReturnEmptyFields2() {
        EntityMetadata metadata = EntityMetadata.of(Product.class);
        Assertions.assertTrue(metadata.getFields().isEmpty());
    }

    @Test
    public void shouldReturnIterable() throws NoSuchFieldException {
        EntityMetadata metadata = EntityMetadata.of(Inventory.class);
        Assertions.assertFalse(metadata.getFields().isEmpty());
        FieldMetadata field = metadata.getFields().get(0);
        Field products = Inventory.class.getDeclaredField("products");
        Assertions.assertEquals(1, metadata.getFields().size());
        Assertions.assertEquals(products, field.get());
    }

    @Test
    public void shouldReturnMap() throws NoSuchFieldException {
        EntityMetadata metadata = EntityMetadata.of(Contact.class);
        Assertions.assertFalse(metadata.getFields().isEmpty());
        FieldMetadata field = metadata.getFields().get(0);
        Field contacts = Contact.class.getDeclaredField("contacts");
        Assertions.assertEquals(contacts, field.get());
        Assertions.assertEquals(1, metadata.getFields().size());
    }

    @Test
    @DisplayName("Should return both Map and Iterable")
    public void shouldReturnMapAndIterable() throws NoSuchFieldException {
        EntityMetadata metadata = EntityMetadata.of(MediaUser.class);
        Assertions.assertFalse(metadata.getFields().isEmpty());
        Assertions.assertEquals(2, metadata.getFields().size());
        FieldMetadata fieldA = metadata.getFields().get(0);
        FieldMetadata fieldB = metadata.getFields().get(1);
        Field medias = MediaUser.class.getDeclaredField("medias");
        Field postsBySocialMedia = MediaUser.class.getDeclaredField("postsBySocialMedia");
        Assertions.assertEquals(fieldA.get(), medias);
        Assertions.assertEquals(fieldB.get(), postsBySocialMedia);
    }

    @Test
    public void shouldReturnNPEWhenIsNull() {
        EntityMetadata metadata = EntityMetadata.of(MediaUser.class);
        Assertions.assertThrows(NullPointerException.class, ()-> metadata.values(null, new String[0]));
    }

    @Test
    public void shouldReturnIllegalErrorWhenTypesAreIncompatibles() {
        EntityMetadata metadata = EntityMetadata.of(MediaUser.class);
        Assertions.assertThrows(IllegalArgumentException.class, ()-> metadata.values(new Cat(), new String[0]));
    }

    @Test
    public void shouldReturnValues() {
        EntityMetadata metadata = EntityMetadata.of(MediaUser.class);
        String user = "Otavio";
        Set<String> medias = null;
        Map<String, String> postsBySocialMedia = Map.of("otaviojava", "my post", "otavio", "my photo");
        MediaUser mediaUser = new MediaUser(user, medias, postsBySocialMedia);
        List<Object> values = metadata.values(mediaUser, new String[0]).collect(Collectors.toList());

        Assertions.assertEquals(1, values.size());
        Assertions.assertEquals(postsBySocialMedia, values.get(0));
    }

    @Test
    public void shouldReturnFilter() {
        EntityMetadata metadata = EntityMetadata.of(MediaUser.class);
        String user = "Otavio";
        Set<String> medias = Set.of("Twitter", "Facebook");
        Map<String, String> postsBySocialMedia = Map.of("otaviojava", "my post", "otavio", "my photo");
        MediaUser mediaUser = new MediaUser(user, medias, postsBySocialMedia);
        List<Object> values = metadata.values(mediaUser, new String[]{"medias"}).collect(Collectors.toList());
        Assertions.assertEquals(1, values.size());
        Assertions.assertEquals(medias, values.get(0));
    }

    @Test
    public void shouldReturnFilter2() {
        EntityMetadata metadata = EntityMetadata.of(MediaUser.class);
        String user = "Otavio";
        Set<String> medias = Set.of("Twitter", "Facebook");
        Map<String, String> postsBySocialMedia = Map.of("otaviojava", "my post", "otavio", "my photo");
        MediaUser mediaUser = new MediaUser(user, medias, postsBySocialMedia);
        List<Object> values = metadata.values(mediaUser, new String[]{"postsBySocialMedia"}).collect(Collectors.toList());
        Assertions.assertEquals(1, values.size());
        Assertions.assertEquals(postsBySocialMedia, values.get(0));
    }

    @Test
    public void shouldReturnEmptyWhenIsNotIterableOrMap() {
        EntityMetadata metadata = EntityMetadata.of(MediaUser.class);
        String user = "Otavio";
        Set<String> medias = Set.of("Twitter", "Facebook");
        Map<String, String> postsBySocialMedia = Map.of("otaviojava", "my post", "otavio", "my photo");
        MediaUser mediaUser = new MediaUser(user, medias, postsBySocialMedia);
        List<Object> values = metadata.values(mediaUser, new String[]{"user"}).collect(Collectors.toList());
        Assertions.assertTrue(values.isEmpty());
    }

    @Test
    public void shouldIgnoreNonIterableOrMap() {
        EntityMetadata metadata = EntityMetadata.of(MediaUser.class);
        String user = "Otavio";
        Set<String> medias = Set.of("Twitter", "Facebook");
        Map<String, String> postsBySocialMedia = Map.of("otaviojava", "my post", "otavio", "my photo");
        MediaUser mediaUser = new MediaUser(user, medias, postsBySocialMedia);
        List<Object> values = metadata.values(mediaUser, new String[]{"user", "medias"}).collect(Collectors.toList());
        Assertions.assertEquals(1, values.size());
        Assertions.assertEquals(medias, values.get(0));
    }

    @Test
    @DisplayName("Should return all Iterable and map fields when it uses the default annotation")
    public void shouldReturnAllFieldsOnDefault() {
        EntityMetadata metadata = EntityMetadata.of(MediaUser.class);
        String user = "Otavio";
        Set<String> medias = Set.of("Twitter", "Facebook");
        Map<String, String> postsBySocialMedia = Map.of("otaviojava", "my post", "otavio", "my photo");
        MediaUser mediaUser = new MediaUser(user, medias, postsBySocialMedia);
        List<Object> values = metadata.values(mediaUser, new String[]{""}).collect(Collectors.toList());
        Assertions.assertEquals(2, values.size());
        Assertions.assertEquals(medias, values.get(0));
        Assertions.assertEquals(postsBySocialMedia, values.get(1));
    }
}