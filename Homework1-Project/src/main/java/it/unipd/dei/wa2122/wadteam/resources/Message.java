/*
 * Copyright 2018 University of Padua, Italy
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

package it.unipd.dei.wa2122.wadteam.resources;

import org.json.JSONObject;

import java.io.*;

/**
 * Represents a message or an error message.
 *
 * @author Nicola Ferro (ferro@dei.unipd.it)
 * @version HW1.00
 * @since 1.00
 */
public class Message implements Resource {

    /**
     * The message
     */
    private final String message;

    /**
     * The code of the error, if any
     */
    private final String errorCode;

    /**
     * Additional details about the error, if any
     */
    private final String errorDetails;

    /**
     * Indicates whether the message is about an error or not.
     */
    private final boolean isError;

    /**
     * Indicates the indicates the generated resource id.
     */
    private final Integer resourceId;


    /**
     * Creates an error message.
     *
     * @param message
     *            the message.
     * @param errorCode
     *            the code of the error.
     * @param errorDetails
     *            additional details about the error.
     */
    public Message(final String message, final String errorCode, final String errorDetails) {
        this.message = message;
        this.errorCode = errorCode;
        this.errorDetails = errorDetails;
        this.isError = true;
        this.resourceId = null;
    }


    /**
     * Creates a generic message.
     *
     * @param message
     *            the message.
     */
    public Message(final String message) {
        this.message = message;
        this.errorCode = null;
        this.errorDetails = null;
        this.isError = false;
        this.resourceId = null;
    }

    public Message(String message, Integer resourceId) {
        this.message = message;
        this.resourceId = resourceId;
        this.errorCode = null;
        this.errorDetails = null;
        this.isError = false;
    }

    /**
     * Returns the message.
     *
     * @return the message.
     */
    public final String getMessage() {
        return message;
    }

    /**
     * Returns the code of the error, if any.
     *
     * @return the code of the error, if any, {@code null} otherwise.
     */
    public final String getErrorCode() {
        return errorCode;
    }

    /**
     * Returns additional details about the error, if any.
     *
     * @return additional details about the error, if any, {@code null} otherwise.
     */
    public final String getErrorDetails() {
        return errorDetails;
    }

    /**
     * Indicates whether the message is about an error or not.
     *
     * @return {@code true} is the message is about an error, {@code false} otherwise.
     */
    public final boolean isError() {
        return isError;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", message);
        if(errorCode != null) {
            jsonObject.put("errorCode", errorCode);
        }
        if(errorDetails != null) {
            jsonObject.put("errorDetails", errorDetails);
        }
        if(resourceId != null) {
            jsonObject.put("resourceId", resourceId);
        }
        jsonObject.put("isError", isError);

        return jsonObject;
    }
}
