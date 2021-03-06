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

package org.flowable.form.spring;

import org.flowable.form.engine.FormEngine;
import org.flowable.form.engine.FormEngineConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Dave Syer
 * @author Tijs Rademakers
 * @author Joram Barrez
 * @author Josh Long
 */
public class FormEngineFactoryBean implements FactoryBean<FormEngine>, DisposableBean, ApplicationContextAware {

  protected FormEngineConfiguration formEngineConfiguration;

  protected ApplicationContext applicationContext;
  protected FormEngine formEngine;

  public void destroy() throws Exception {
    if (formEngine != null) {
      formEngine.close();
    }
  }

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  public FormEngine getObject() throws Exception {
    configureExternallyManagedTransactions();

    this.formEngine = formEngineConfiguration.buildFormEngine();
    return this.formEngine;
  }

  protected void configureExternallyManagedTransactions() {
    if (formEngineConfiguration instanceof SpringFormEngineConfiguration) { // remark: any config can be injected, so we cannot have SpringConfiguration as member
      SpringFormEngineConfiguration engineConfiguration = (SpringFormEngineConfiguration) formEngineConfiguration;
      if (engineConfiguration.getTransactionManager() != null) {
        formEngineConfiguration.setTransactionsExternallyManaged(true);
      }
    }
  }

  public Class<FormEngine> getObjectType() {
    return FormEngine.class;
  }

  public boolean isSingleton() {
    return true;
  }

  public FormEngineConfiguration getFormEngineConfiguration() {
    return formEngineConfiguration;
  }

  public void setFormEngineConfiguration(FormEngineConfiguration formEngineConfiguration) {
    this.formEngineConfiguration = formEngineConfiguration;
  }
}
