/*
 * Copyright (c) 2022, Robert Scholte and others by the @author tag.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */
package org.javamoney.jakarta.xml.bind;

import javax.money.CurrencyUnit;
import javax.money.UnknownCurrencyException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CurrencyUnitAdapterTest
{
    private CurrencyUnitAdapter adapter = new CurrencyUnitAdapter();

    @Test
    void marshalNull() throws Exception
    {
        Assertions.assertNull( adapter.marshal( null ) );
    }

    @Test
    void unmarshalNull() throws Exception
    {
        Assertions.assertNull( adapter.unmarshal( null ) );
    }

    @Test
    void unmarshalValidString() throws Exception
    {
        CurrencyUnit monetaryAmount = adapter.unmarshal( "EUR" ); 
        Assertions.assertNotNull( monetaryAmount );
        Assertions.assertEquals( "EUR", monetaryAmount.toString() ); 
    }

    @Test
    void unmarshalInvalidString() throws Exception
    {
        Assertions.assertThrows( UnknownCurrencyException.class, () -> adapter.unmarshal( "euros" ) );
    }
}
