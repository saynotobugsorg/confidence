/*
 * Copyright 2023 dmfs GmbH
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.saynotobugs.confidence.asm.quality;

import org.dmfs.jems2.iterable.Seq;
import org.dmfs.jems2.iterable.Sieved;
import org.dmfs.jems2.procedure.ForEach;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.saynotobugs.confidence.asm.AnnotationAdapter;

import java.lang.Class;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

final class AnnotationNodeAdapter extends AnnotationVisitor implements AnnotationAdapter
{
    private final String mDescriptor;
    private final Map<String, Object> mParams = new HashMap<>();

    public AnnotationNodeAdapter(String descriptor)
    {
        super(Opcodes.ASM9);
        mDescriptor = descriptor;
    }

    @Override
    public void visit(String name, Object value)
    {
        mParams.put(name, value instanceof Type ? new ClassFunction().value((Type) value) : value);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String name, String descriptor)
    {
        AnnotationNodeAdapter annotationNodeAdapter = new AnnotationNodeAdapter(descriptor);
        mParams.put(name, annotationNodeAdapter.annotation());
        return annotationNodeAdapter;
    }

    @Override
    public AnnotationVisitor visitArray(String name)
    {
        return new ArrayAnnotationVisitor(mDescriptor, name, mParams::put);
    }

    @Override
    public void visitEnum(String name, String descriptor, String value)
    {
        new ForEach<>(new Sieved<>(v -> ((Enum) v).name().equals(value),
            new Seq<>(new ClassFunction().value(Type.getType(descriptor)).getEnumConstants())))
            .process(enumValue -> mParams.put(name, enumValue));
    }


    @Override
    public Annotation annotation()
    {
        return (Annotation) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
            new Class[] { new ClassFunction().value(Type.getType(mDescriptor)) },
            new AnnotationInvocationHandler(Type.getType(mDescriptor).getClassName(), mParams));
    }
}
