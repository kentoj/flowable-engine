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
package org.flowable.rest.form.service.api.form;

import javax.servlet.http.HttpServletRequest;

import org.flowable.engine.common.api.FlowableIllegalArgumentException;
import org.flowable.engine.common.api.FlowableObjectNotFoundException;
import org.flowable.form.api.FormService;
import org.flowable.form.model.FormModel;
import org.flowable.rest.form.FormRestResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yvo Swillens
 */
@RestController
public class FormInstanceResource {

  @Autowired
  protected FormService formService;

  @Autowired
  protected FormRestResponseFactory formRestResponseFactory;

  @RequestMapping(value = "/form/form-instance/{formInstanceId}", method = RequestMethod.GET, produces = "application/json")
  public FormInstanceResponse getFormInstance(@PathVariable String formInstanceId, HttpServletRequest request) {
    return formRestResponseFactory.createFormInstanceResponse(formService.createFormInstanceQuery().id(formInstanceId).singleResult());
  }

  @RequestMapping(value = "/form/form-instance", method = RequestMethod.POST, produces = "application/json")
  public void storeFormInstance(@RequestBody FormRequest formRequest, HttpServletRequest request) {

    FormModel formModel;

    if (formRequest.getFormDefinitionKey() != null) {
      formModel = formService.getFormModelWithVariablesByKey(
          formRequest.getFormDefinitionKey(),
          formRequest.getProcessInstanceId(),
          formRequest.getVariables(),
          formRequest.getTenantId()
      );
    } else if (formRequest.getFormDefinitionId() != null) {
      formModel = formService.getFormModelWithVariablesById(
          formRequest.getFormDefinitionId(),
          formRequest.getProcessInstanceId(),
          formRequest.getVariables(),
          formRequest.getTenantId()
      );
    } else {
      throw new FlowableIllegalArgumentException("Either form definition key or form definition id must be provided in the request");
    }

    if (formModel == null) {
      throw new FlowableObjectNotFoundException("Could not find a form definition");
    }

    formService.createFormInstance(formRequest.getVariables(), formModel, formRequest.getTaskId(),
        formRequest.getProcessInstanceId());
  }
}
