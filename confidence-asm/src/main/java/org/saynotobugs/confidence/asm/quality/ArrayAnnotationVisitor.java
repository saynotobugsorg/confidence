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

import org.dmfs.jems2.BiProcedure;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.jems2.iterable.Sieved;
import org.dmfs.jems2.procedure.ForEach;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

final class ArrayAnnotationVisitor extends AnnotationVisitor
{
    private final List<Object> mArray = new ArrayList<>();
    private final String mAnnotationDescriptor;
    private final String mMethodName;

    private final BiProcedure<String, Object> mUpdateAnnotation;

    public ArrayAnnotationVisitor(String annotationDescriptor,
        String methodName,
        BiProcedure<String, Object> updateAnnotation)
    {
        super(Opcodes.ASM9);
        mAnnotationDescriptor = annotationDescriptor;
        mMethodName = methodName;
        mUpdateAnnotation = updateAnnotation;
    }

    @Override
    public void visit(String inner, Object value)
    {
        mArray.add(value instanceof Type ? new ClassFunction().value((Type) value) : value);
    }

    @Override
    public void visitEnum(String name, String descriptor, String value)
    {
        new ForEach<>(new Sieved<>(v -> ((Enum) v).name().equals(value),
            new Seq<>(new ClassFunction().value(Type.getType(descriptor)).getEnumConstants())))
            .process(mArray::add);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String name, String descriptor)
    {
        AnnotationNodeAdapter annotationNodeAdapter = new AnnotationNodeAdapter(descriptor);
        mArray.add(annotationNodeAdapter.annotation());
        return annotationNodeAdapter;
    }

    @Override
    public void visitEnd()
    {
        Object r = Array.newInstance(
            new MethodFunction(new ClassFunction().value(Type.getType(mAnnotationDescriptor)))
                .value(mMethodName).getReturnType().getComponentType(), mArray.size());
        for (int i = 0; i < mArray.size(); ++i)
        {
            Array.set(r, i, mArray.get(i));
        }
        mUpdateAnnotation.process(mMethodName, r);
    }
}
