/*
 * Copyright (c) 2010-2014. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.axonframework.serializer.converters;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.axonframework.serializer.AbstractContentTypeConverter;
import org.axonframework.serializer.CannotConvertBetweenTypesException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Converter that converts an InputStream to a byte array. This converter simply reads all contents from the
 * input stream and returns that as an array.
 *
 * @author Allard Buijze
 * @since 2.0
 */
public class InputStreamToByteArrayConverter extends AbstractContentTypeConverter<InputStream, byte[]> {

    @Override
    public Class<InputStream> expectedSourceType() {
        return InputStream.class;
    }

    @Override
    public Class<byte[]> targetType() {
        return byte[].class;
    }

    @Override
    public byte[] convert(InputStream original) {
        try {
            return bytesFrom(original);
        } catch (IOException e) {
            throw new CannotConvertBetweenTypesException("Unable to convert InputStream to byte[]. "
                                                                 + "Error while reading from Stream.", e);
        }
    }

    private byte[] bytesFrom(InputStream original) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream(); // NOSONAR - There is no point in closing BAOS
        byte[] buffer = new byte[1024];
        int n;
        while (-1 != (n = original.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
}
