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

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryParseException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MonetaryAmountAdapterTest
{
    private MonetaryAmountAdapter adapter = new MonetaryAmountAdapter();

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
        MonetaryAmount monetaryAmount = adapter.unmarshal( "EUR 1.01" ); 
        Assertions.assertNotNull( monetaryAmount );
        Assertions.assertEquals( "EUR 1.01", monetaryAmount.toString() ); 
    }

    @Test
    void unmarshalInvalidString() throws Exception
    {
        Assertions.assertThrows( MonetaryParseException.class, () -> adapter.unmarshal( "ten euros" ) );
    }
}
