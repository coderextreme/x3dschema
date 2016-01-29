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
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.Schema;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONException;

import java.io.*;
import java.util.*;

public class ObjectTest {
    public static void validateObject(String file) throws FileNotFoundException {

        JSONObject jsonSchema = new JSONObject(new JSONTokener(
                ObjectTest.class
                        .getResourceAsStream("x3d-3.3-JSONSchema.json")));

	JSONTokener tokener = new JSONTokener(new FileInputStream(file));
	JSONObject jsonSubject = new JSONObject(tokener);

        Schema schema = SchemaLoader.load(jsonSchema);
        schema.validate(jsonSubject);
    }
    public static void main(String args[]) {
    	for (int a = 0; a < args.length; a++) {
		System.out.println("BEGIN "+args[a]);
		try {
			validateObject(args[a]);
			System.out.println("json-schema Valid");
		} catch (FileNotFoundException e) {
			System.out.println("json-file file "+e.getMessage());
		} catch (JSONException je) {
			System.out.println("json-parse json "+je.getMessage());
		} catch (ValidationException ve) {
			Iterator<ValidationException> i = ve.getCausingExceptions().iterator();
			while (i.hasNext()) {
				ValidationException veel = i.next();
				System.out.println("json-schema Validation "+veel);
			}
		}
		System.out.println("END "+args[a]);
	}
    }
}
