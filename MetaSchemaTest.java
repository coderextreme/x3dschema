/*
 * Copyright (C) 2011 Everit Kft. (http://www.everit.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.everit.json.schema.loader.SchemaLoader;
import org.everit.json.schema.Schema;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MetaSchemaTest {
  public static void main(String args[]) {

    JSONObject jsonSchema = new JSONObject(new JSONTokener(
        MetaSchemaTest.class
            .getResourceAsStream("json-schema-draft-04.json")));

    JSONObject jsonSubject = new JSONObject(new JSONTokener(
        MetaSchemaTest.class
            .getResourceAsStream("x3d-3.3-JSONSchema.json")));

    Schema schema = SchemaLoader.load(jsonSchema);
    schema.validate(jsonSubject);
  }

}
