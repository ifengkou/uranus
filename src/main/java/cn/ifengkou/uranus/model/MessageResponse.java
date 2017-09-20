/**
 * Copyright (C) 2016 Newland Group Holding Limited
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ifengkou.uranus.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * MessageResponse 应答结构
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/2/22 17:26
 */
public class MessageResponse implements Serializable {

    private String messageId;
    private String error;
    private Object resultDesc;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getResult() {
        return resultDesc;
    }

    public void setResult(Object resultDesc) {
        this.resultDesc = resultDesc;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}

