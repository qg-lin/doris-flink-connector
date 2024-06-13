// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package org.apache.doris.flink.cfg;

import java.util.Objects;

import static org.apache.flink.util.Preconditions.checkNotNull;

/** Options for the Doris connector. */
public class DorisOptions extends DorisConnectionOptions {

    private static final long serialVersionUID = 1L;

    private String tableIdentifier;
    private String charsetEncoding = "UTF-8";

    public DorisOptions(String fenodes, String username, String password, String tableIdentifier) {
        super(fenodes, username, password);
        this.tableIdentifier = tableIdentifier;
    }

    public DorisOptions(
            String fenodes,
            String username,
            String password,
            String tableIdentifier,
            String jdbcUrl) {
        super(fenodes, username, password, jdbcUrl);
        this.tableIdentifier = tableIdentifier;
    }

    public DorisOptions(
            String fenodes,
            String beNodes,
            String username,
            String password,
            String tableIdentifier,
            String jdbcUrl,
            boolean redirect,
            String charsetEncoding) {
        super(fenodes, beNodes, username, password, jdbcUrl, redirect);
        this.tableIdentifier = tableIdentifier;
        this.charsetEncoding = charsetEncoding;
    }

    public String getTableIdentifier() {
        return tableIdentifier;
    }

    public void setTableIdentifier(String tableIdentifier) {
        this.tableIdentifier = tableIdentifier;
    }

    public String getCharsetEncoding() {
        return charsetEncoding;
    }

    public void setCharsetEncoding(String charsetEncoding) {
        this.charsetEncoding = charsetEncoding;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DorisOptions that = (DorisOptions) o;
        return Objects.equals(tableIdentifier, that.tableIdentifier)
                && autoRedirect == that.autoRedirect
                && Objects.equals(fenodes, that.fenodes)
                && Objects.equals(username, that.username)
                && Objects.equals(password, that.password)
                && Objects.equals(jdbcUrl, that.jdbcUrl)
                && Objects.equals(benodes, that.benodes)
                && Objects.equals(charsetEncoding, that.charsetEncoding);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                fenodes,
                username,
                password,
                jdbcUrl,
                benodes,
                autoRedirect,
                tableIdentifier,
                charsetEncoding);
    }

    /** Builder of {@link DorisOptions}. */
    public static class Builder {
        private String fenodes;
        private String benodes;
        private String jdbcUrl;
        private String username;
        private String password;
        private boolean autoRedirect = true;
        private String tableIdentifier;
        private String charsetEncoding = "UTF-8";

        /** required, tableIdentifier. */
        public Builder setTableIdentifier(String tableIdentifier) {
            this.tableIdentifier = tableIdentifier;
            return this;
        }

        /** optional, user name. */
        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        /** optional, password. */
        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        /** required, Frontend Http Rest url. */
        public Builder setFenodes(String fenodes) {
            this.fenodes = fenodes;
            return this;
        }

        /** optional, Backend Http Port. */
        public Builder setBenodes(String benodes) {
            this.benodes = benodes;
            return this;
        }

        /** not required, fe jdbc url, for lookup query. */
        public Builder setJdbcUrl(String jdbcUrl) {
            this.jdbcUrl = jdbcUrl;
            return this;
        }

        public Builder setAutoRedirect(boolean autoRedirect) {
            this.autoRedirect = autoRedirect;
            return this;
        }

        /** optional, Charset encoding for Http client, default UTF-8. */
        public Builder setCharsetEncoding(String charsetEncoding) {
            this.charsetEncoding = charsetEncoding;
            return this;
        }

        public DorisOptions build() {
            checkNotNull(fenodes, "No fenodes supplied.");
            // multi table load, don't need check
            // checkNotNull(tableIdentifier, "No tableIdentifier supplied.");
            return new DorisOptions(
                    fenodes,
                    benodes,
                    username,
                    password,
                    tableIdentifier,
                    jdbcUrl,
                    autoRedirect,
                    charsetEncoding);
        }
    }
}
