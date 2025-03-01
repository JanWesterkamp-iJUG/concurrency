/*
 * Copyright (c) 2013, 2023 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.concurrent.common.tasks;

import java.util.concurrent.TimeUnit;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import ee.jakarta.tck.concurrent.framework.TestLogger;

/**
 * the Runnable Task to check the context related job. it verifies : 1, load
 * class and 2, look up env-entry set to the deployment descriptor.
 */
public class RunnableTask implements Runnable {

	private static final TestLogger log = TestLogger.get(RunnableTask.class);

	private final String jndiName;

	private final String expectedJndiValue;

	private final String contexualClassName;

	private final long blockTime;
	
	private volatile int count = 0;

	@Override
	public void run() {
		if (blockTime > 0) {
			try {
				TimeUnit.MILLISECONDS.sleep(blockTime);
			} catch (InterruptedException e) {
				log.severe("", e);
			}
		}
		boolean jndiPassed = lookupEnvRef();
		boolean loadClassPassed = loadClass();
		if (!(jndiPassed && loadClassPassed)) {
			throw new RuntimeException(
					"jndi test passed: " + jndiPassed + ", class loading test passed: " + loadClassPassed);
		}
		count++;
	}

	/**
	 * Construct the runnable task with expected properties.
	 * 
	 * @param jndiName  the jndi name set for env-entry, ignore jndi test if it is
	 *                  null.
	 * @param jndiValue the jndi value set for jndiName
	 * @param className class name to be loaded inside the task, ignore class
	 *                  loading test if it is null.
	 * @param blockTime block time(in millisecond) for this task.
	 */
	public RunnableTask(String jndiName, String jndiValue, String className, long blockTime) {
		this.contexualClassName = className;
		this.jndiName = jndiName;
		this.expectedJndiValue = jndiValue;
		this.blockTime = blockTime;
	}

	/**
	 * Construct the runnable task with expected properties.
	 * 
	 * @param jndiName  the jndi name set for env-entry, ignore jndi test if it is
	 *                  null.
	 * @param jndiValue the jndi value set for jndiName
	 * @param className class name to be loaded inside the task, ignore class
	 *                  loading test if it is null.
	 */
	public RunnableTask(String jndiName, String jndiValue, String className) {
		this.contexualClassName = className;
		this.jndiName = jndiName;
		this.expectedJndiValue = jndiValue;
		this.blockTime = 0;
	}

	protected boolean lookupEnvRef() {
		boolean passed = false;
		String value = null;
		try {
			value = InitialContext.doLookup(jndiName);
			if (expectedJndiValue.equals(value)) {
				passed = true;
			}
		} catch (NamingException e) {
		}

		return passed;
	}
	
    public int getCount() {
        return count;
    }

	protected boolean loadClass() {
		boolean passed = false;
		try {
			Class<?> loadedClass = Thread.currentThread().getContextClassLoader().loadClass(contexualClassName);
			if (contexualClassName == loadedClass.getName()) {
				passed = true;
			}
		} catch (ClassNotFoundException e) {
		}
		return passed;
	}

}
