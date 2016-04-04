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

public class NumberTest {
    public static void main(String args[]) {
	args = new String [] { "number.json" };
    	for (int a = 0; a < args.length; a++) {
		System.out.println(args[a]);
		String version = "Unknown";
		try {
			System.out.println("Starting");
			JSONTokener tokener = new JSONTokener(new FileInputStream(args[a]));
			JSONObject jsonSubject = new JSONObject(tokener);
			System.out.println(jsonSubject.getJSONObject("MovieTexture").get("@startTime").getClass().getSimpleName());
			JSONObject jsonSchema = new JSONObject(new JSONTokener(
				NumberTest.class
					.getResourceAsStream("numberschema.json")));

			Schema schema = SchemaLoader.load(jsonSchema);
			schema.validate(jsonSubject);
			System.out.println("Finishing");
			
			System.out.println("json-schema Valid "+args[a]);
		} catch (NullPointerException e) {
			System.out.println("json-config null point error "+args[a]);
		} catch (FileNotFoundException e) {
			System.out.println("json-file file missing "+e.getMessage()+" "+args[a]);
		} catch (JSONException je) {
			System.out.println("json-parse json "+je.getMessage()+" "+args[a]);
		} catch (ValidationException ve) {
			ve.printStackTrace();
			System.out.println("json-schema Validation error "+ve+" "+args[a]);
			Iterator<ValidationException> i = ve.getCausingExceptions().iterator();
			while (i.hasNext()) {
				ValidationException veel = i.next();
				System.out.println("json-schema Validation error "+veel+" "+args[a]);
			}
		}
	}
    }
}
