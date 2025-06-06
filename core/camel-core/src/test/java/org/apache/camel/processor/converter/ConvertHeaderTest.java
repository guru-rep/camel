/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.processor.converter;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Date;

import org.apache.camel.ContextTestSupport;
import org.apache.camel.Exchange;
import org.apache.camel.NoTypeConversionAvailableException;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.TypeConversionException;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ConvertHeaderTest extends ContextTestSupport {

    @Test
    public void testConvertBodyTo() {
        try {
            context.addRoutes(new RouteBuilder() {
                public void configure() {
                    // set an invalid charset
                    from("direct:invalid").convertHeaderTo("foo", String.class, "ASSI").to("mock:endpoint");
                }
            });
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertIsInstanceOf(UnsupportedCharsetException.class, e.getCause());
        }
    }

    @Test
    public void testConvertBodyCharset() throws Exception {
        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("direct:foo").convertHeaderTo("foo", byte[].class, "iso-8859-1").to("mock:foo");
            }
        });

        getMockEndpoint("mock:foo").expectedMessageCount(1);
        // do not propagate charset to avoid side effects with double conversion
        // etc
        getMockEndpoint("mock:foo").message(0).exchangeProperty(Exchange.CHARSET_NAME).isNull();

        template.sendBodyAndHeader("direct:foo", null, "foo", "Hello World");

        assertMockEndpointsSatisfied();
    }

    @Test
    public void testConvertBodyCharsetWithExistingCharsetName() throws Exception {
        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("direct:foo").convertHeaderTo("foo", byte[].class, "iso-8859-1").to("mock:foo");
            }
        });

        getMockEndpoint("mock:foo").expectedMessageCount(1);
        // do not propagate charset to avoid side effects with double conversion
        // etc
        getMockEndpoint("mock:foo").message(0).exchangeProperty(Exchange.CHARSET_NAME).isEqualTo("UTF-8");

        Exchange srcExchange = ExchangeBuilder.anExchange(context).withProperty(Exchange.CHARSET_NAME, "UTF-8")
                .withHeader("foo", "Hello World").build();

        template.send("direct:foo", srcExchange);

        assertMockEndpointsSatisfied();
    }

    @Test
    public void testConvertToInteger() throws Exception {
        MockEndpoint result = getMockEndpoint("mock:result");
        result.expectedHeaderReceived("foo", 11);

        template.sendBodyAndHeader("direct:start", null, "foo", 11);

        assertMockEndpointsSatisfied();
    }

    @Test
    public void testConvertToName() throws Exception {
        MockEndpoint result = getMockEndpoint("mock:result");
        result.expectedHeaderReceived("foo", "11");
        result.expectedHeaderReceived("bar", 11);
        result.message(0).header("foo").isInstanceOf(String.class);
        result.message(0).header("bar").isInstanceOf(Integer.class);

        template.sendBodyAndHeader("direct:bar", null, "foo", "11");

        assertMockEndpointsSatisfied();
    }

    @Test
    public void testConvertToIntegerNotMandatory() throws Exception {
        // mandatory should fail
        try {
            template.sendBodyAndHeader("direct:start", null, "foo", "xxx");
            fail();
        } catch (Exception e) {
            assertIsInstanceOf(TypeConversionException.class, e.getCause());
        }

        // optional should cause null body
        getMockEndpoint("mock:result").expectedMessageCount(1);
        getMockEndpoint("mock:result").message(0).body().isNull();

        template.sendBodyAndHeader("direct:optional", null, "foo", Double.NaN);

        assertMockEndpointsSatisfied();
    }

    @Test
    public void testConvertNullBody() throws Exception {
        MockEndpoint result = getMockEndpoint("mock:result");
        result.expectedMessageCount(1);
        result.message(0).header("foo").isNull();

        template.sendBodyAndHeader("direct:start", null, "foo", null);

        assertMockEndpointsSatisfied();
    }

    @Test
    public void testConvertFailed() throws Exception {
        getMockEndpoint("mock:result").expectedMessageCount(0);

        try {
            template.sendBodyAndHeader("direct:invalid", null, "foo", "11");
            fail("Should have thrown an exception");
        } catch (RuntimeCamelException e) {
            boolean b = e.getCause() instanceof NoTypeConversionAvailableException;
            assertTrue(b);
        }

        assertMockEndpointsSatisfied();
    }

    @Test
    public void testConvertToBytesCharset() throws Exception {
        byte[] body = "Hello World".getBytes(StandardCharsets.ISO_8859_1);

        MockEndpoint result = getMockEndpoint("mock:result");
        result.expectedHeaderReceived("foo", body);

        template.sendBodyAndHeader("direct:charset", null, "foo", "Hello World");

        assertMockEndpointsSatisfied();
    }

    @Test
    public void testConvertToStringCharset() throws Exception {
        String body = "Hello World";

        MockEndpoint result = getMockEndpoint("mock:result");
        result.expectedHeaderReceived("foo", body);

        template.sendBodyAndHeader("direct:charset3", null, "foo", new ByteArrayInputStream(
                body.getBytes(
                        StandardCharsets.UTF_16)));

        assertMockEndpointsSatisfied();
    }

    @Test
    public void testConvertToBytesCharsetFail() throws Exception {
        byte[] body = "Hello World".getBytes(StandardCharsets.UTF_8);

        MockEndpoint result = getMockEndpoint("mock:result");
        result.expectedHeaderReceived("foo", body);
        result.expectedMessageCount(1);

        template.sendBodyAndHeader("direct:charset2", null, "foo", "Hello World");

        // should NOT be okay as we expected utf-8 but got it in utf-16
        result.assertIsNotSatisfied();
    }

    // does not work on AIX
    @DisabledOnOs(OS.AIX)
    @Test
    public void testConvertToStringCharsetFail() throws Exception {
        String body = "Hell\u00F6 W\u00F6rld";

        MockEndpoint result = getMockEndpoint("mock:result");
        result.expectedHeaderReceived("foo", body);
        result.expectedMessageCount(1);

        template.sendBodyAndHeader("direct:charset3", null, "foo", new ByteArrayInputStream(
                body.getBytes(
                        StandardCharsets.UTF_8)));

        // should NOT be okay as we expected utf-8 but got it in utf-16
        result.assertIsNotSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("direct:start").convertHeaderTo("foo", Integer.class).to("mock:result");
                from("direct:optional").convertHeaderTo("foo", Integer.class, false).to("mock:result");
                from("direct:invalid").convertHeaderTo("foo", Date.class).to("mock:result");
                from("direct:charset").convertHeaderTo("foo", byte[].class, "iso-8859-1").to("mock:result");
                from("direct:charset2").convertHeaderTo("foo", byte[].class, "utf-16").to("mock:result");
                from("direct:charset3").convertHeaderTo("foo", String.class, "utf-16").to("mock:result");
                from("direct:bar").convertHeaderTo("foo", "bar", Integer.class).to("mock:result");
            }
        };
    }

}
