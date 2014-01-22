/*
 * Copyright (c) 2008-2013, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.executor;

import com.hazelcast.core.MemberLeftException;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;
import com.hazelcast.spi.ExceptionAction;
import com.hazelcast.spi.exception.TargetNotMemberException;

import java.util.concurrent.Callable;

/**
 * @author mdogan 1/18/13
 */
public final class MemberCallableTaskOperation extends BaseCallableTaskOperation
        implements IdentifiedDataSerializable {

    public MemberCallableTaskOperation() {
    }

    public MemberCallableTaskOperation(String name, String uuid, Callable callable) {
        super(name, uuid, callable);
    }

    @Override
    public ExceptionAction onException(Throwable throwable) {
        if (throwable instanceof MemberLeftException || throwable instanceof TargetNotMemberException) {
            return ExceptionAction.THROW_EXCEPTION;
        }
        return super.onException(throwable);
    }

    @Override
    public int getFactoryId() {
        return ExecutorDataSerializerHook.F_ID;
    }

    @Override
    public int getId() {
        return ExecutorDataSerializerHook.MEMBER_CALLABLE_TASK;
    }
}
