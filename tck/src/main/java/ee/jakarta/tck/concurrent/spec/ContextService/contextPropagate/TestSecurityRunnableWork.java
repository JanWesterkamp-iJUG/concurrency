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

package ee.jakarta.tck.concurrent.spec.ContextService.contextPropagate;

import java.util.ServiceLoader;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import ee.jakarta.tck.concurrent.framework.EJBJNDIProvider;

@SuppressWarnings("serial")
public class TestSecurityRunnableWork extends BaseTestRunnableWork {

	@Override
	protected String work() {
		EJBJNDIProvider nameProvider = ServiceLoader.load(EJBJNDIProvider.class).findFirst().orElseThrow();
		LimitedInterface limited;
        try {
            limited = InitialContext.doLookup(nameProvider.getEJBJNDIName());
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
		return limited.doSomething();
	}
}
