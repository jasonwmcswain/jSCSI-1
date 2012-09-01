/**
 * Copyright (c) 2012, University of Konstanz, Distributed Systems Group
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the University of Konstanz nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.jscsi.utils;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.Test;
import java.nio.ByteBuffer;

import org.jscsi.parser.Constants;

/**
 * Tests the correct parsing of several <em>ethereal</em> trace logs.
 * 
 * @author Volker Wildi
 */
public class WiresharkMessageParserTest {

    /** Testing of a validate parsing process of a single line message. */
    @Test
    public void testParseSingleLineMessage() {

        String str = "43 00 02 02";
        int result = 1124073986;

        int[] test = WiresharkMessageParser.parseToIntArray(str);
        assertEquals(test[0], result);
    }

    /** Testing of a validate parsing process of multiple lines message. */
    @Test
    public void testParseMultiLineMessage() {

        String str = "43 00 02 02 69 74 69 61";
        int[] result = { 1124073986, 1769236833 };

        int[] test = WiresharkMessageParser.parseToIntArray(str);

        assertEquals(result.length, test.length);
        for (int i = 0; i < test.length; i++) {
            assertEquals(test[i], result[i]);
        }
    }

    /** Testing of a validate parsing process of a single line message. */
    @Test
    public void testParseSingleLineMessageToByteBuffer() {

        String str = "43 00 02 02";

        int result = 1124073986;
        ByteBuffer resultBuffer = ByteBuffer.allocate(Constants.BYTES_PER_INT);
        resultBuffer.putInt(result).rewind();

        ByteBuffer test = WiresharkMessageParser.parseToByteBuffer(str);
        assertTrue(resultBuffer.equals(test));
    }

    /** Testing of a validate parsing process of multiple lines message. */
    @Test
    public void testParseMultiLineMessageToByteBuffer() {

        String str = "43 00 02 02 69 74 69 61";

        int[] result = { 1124073986, 1769236833 };
        ByteBuffer resultBuffer = ByteBuffer.allocate(result.length
                * Constants.BYTES_PER_INT);

        for (int n : result) {
            resultBuffer.putInt(n);
        }
        resultBuffer.rewind();

        ByteBuffer test = WiresharkMessageParser.parseToByteBuffer(str);
        assertTrue(resultBuffer.equals(test));
    }
}
