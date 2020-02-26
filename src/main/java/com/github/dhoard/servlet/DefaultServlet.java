/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.dhoard.servlet;

import com.github.dhoard.Configuration;
import com.github.dhoard.util.JSONUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.Duration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(DefaultServlet.class.getName());

	private static final long SLEEP = Duration.ofMinutes(1).toMillis();

	public void setConfiguration(Configuration configuration) {
		// DO NOTHING
	}

	public void doHead(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
	throws IOException {
		// Set standard HTTP/1.1 no-cache headers.
		httpServletResponse.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

		// Set standard HTTP/1.0 no-cache header.
		httpServletResponse.setHeader("Pragma", "no-cache");

		httpServletResponse.setStatus(200);

		httpServletResponse.setContentType("text/plain");
		httpServletResponse.getWriter().println("ALIVE");
	}

	public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
	throws IOException {
		// Set standard HTTP/1.1 no-cache headers.
		httpServletResponse.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

		// Set standard HTTP/1.0 no-cache header.
		httpServletResponse.setHeader("Pragma", "no-cache");

		httpServletResponse.setStatus(200);

		httpServletResponse.setContentType("text/plain");
		httpServletResponse.getWriter().println("ALIVE");
	}

	public void doPut(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
		throws IOException {
		doPost(httpServletRequest, httpServletResponse);
	}

	public void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
		throws IOException {
		BufferedReader reader = null;
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;

		try {
			reader = httpServletRequest.getReader();

			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (Exception e) {
			logger.error("Exception", e);
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (Throwable t) {
					// DO NOTHING
				}
			}
		}

		String requestBody = stringBuilder.toString();
		logger.info("requestBody = [" + requestBody + "]");

		JSONObject jsonObject = JSONUtil.parseJSONObject(requestBody);

		long delay = jsonObject.getLong("delay");
		int statusCode = jsonObject.getInt("statusCode");

		if (delay > 0) {
			logger.info("delaying " + delay + " ms...");

			try {
				Thread.sleep(delay);
			} catch (Throwable t) {
				// DO NOTHING
			}
		}

		logger.info("sending " + statusCode + " response...");

		// Set standard HTTP/1.1 no-cache headers.
		httpServletResponse.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

		// Set standard HTTP/1.0 no-cache header.
		httpServletResponse.setHeader("Pragma", "no-cache");

		httpServletResponse.setStatus(statusCode);

		httpServletResponse.setContentType("text/plain");
		httpServletResponse.getWriter().println(requestBody);

		logger.info("done");
	}

	public void doDelete(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
		throws IOException {
			// Set standard HTTP/1.1 no-cache headers.
			httpServletResponse.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

			// Set standard HTTP/1.0 no-cache header.
			httpServletResponse.setHeader("Pragma", "no-cache");

			httpServletResponse.setStatus(500);

			httpServletResponse.setContentType("text/plain");
			httpServletResponse.getWriter().println("ERROR");
	}
}
