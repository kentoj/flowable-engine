/* Licensed under the Apache License, Version 2.0 (the "License");
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

package org.flowable.engine.test.transactions;

import org.flowable.engine.common.api.FlowableException;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.impl.delegate.ActivityBehavior;
import org.flowable.engine.impl.test.PluggableFlowableTestCase;
import org.flowable.engine.test.Deployment;

/**
 * @author Tom Baeyens
 */
public class TransactionRollbackTest extends PluggableFlowableTestCase {

  public static class Buzzz implements ActivityBehavior {
    
    private static final long serialVersionUID = 1L;

    public void execute(DelegateExecution execution) {
      throw new FlowableException("Buzzz");
    }
  }

  @Deployment
  public void testRollback() {
    try {
      runtimeService.startProcessInstanceByKey("RollbackProcess");

      fail("Starting the process instance should throw an exception");

    } catch (Exception e) {
      assertEquals("Buzzz", e.getMessage());
    }

    assertEquals(0, runtimeService.createExecutionQuery().count());
  }

  @Deployment(resources = { "org/flowable/engine/test/transactions/trivial.bpmn20.xml", "org/flowable/engine/test/transactions/rollbackAfterSubProcess.bpmn20.xml" })
  public void testRollbackAfterSubProcess() {
    try {
      runtimeService.startProcessInstanceByKey("RollbackAfterSubProcess");

      fail("Starting the process instance should throw an exception");

    } catch (Exception e) {
      assertEquals("Buzzz", e.getMessage());
    }

    assertEquals(0, runtimeService.createExecutionQuery().count());

  }
}
