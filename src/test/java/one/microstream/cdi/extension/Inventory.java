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

import java.util.ArrayList;
import java.util.List;

/**
 * this class servers as root node for our custom data structure
 *
 */
public class Inventory {

    private final List<Product> products = new ArrayList<>();

    private final String name;

    public Inventory(String name) {
        this.name = name;
    }

    public void add(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Inventory [products=" + products + ", name=" + name + "]";
    }

    public String getName() {
        return name;
    }

}