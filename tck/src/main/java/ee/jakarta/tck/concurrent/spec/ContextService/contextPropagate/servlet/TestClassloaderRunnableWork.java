/*
 * Copyright (c) 2013, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package ee.jakarta.tck.concurrent.spec.ContextService.contextPropagate.servlet;

import ee.jakarta.tck.concurrent.framework.TestConstants;

@SuppressWarnings("serial")
public class TestClassloaderRunnableWork extends BaseTestRunnableWork {

	@Override
	protected String work() {
		try {
			Thread.currentThread().getContextClassLoader().loadClass(
					"ee.jakarta.tck.concurrent.spec.ContextService.contextPropagate.servlet.ProxyCreatorServlet");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		return TestConstants.ComplexReturnValue;
	}
}
