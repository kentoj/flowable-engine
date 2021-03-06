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

package org.flowable.rest.service.api.management;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.*;

import org.flowable.engine.ManagementService;
import org.flowable.engine.common.api.FlowableObjectNotFoundException;
import org.flowable.rest.service.api.RestResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Frederik Heremans
 */
@RestController
@Api(tags = { "Database tables" }, description = "Manage Database tables")
public class TableResource {

  @Autowired
  protected RestResponseFactory restResponseFactory;

  @Autowired
  protected ManagementService managementService;

  @ApiOperation(value = "Get a single table", tags = {"Database tables"})
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Indicates the table exists and the table count is returned."),
          @ApiResponse(code = 404, message = "Indicates the requested table does not exist.")
  })
  @RequestMapping(value = "/management/tables/{tableName}", method = RequestMethod.GET, produces = "application/json")
  public TableResponse getTable(@ApiParam(name = "tableName") @PathVariable String tableName, HttpServletRequest request) {
    Map<String, Long> tableCounts = managementService.getTableCount();

    TableResponse response = null;
    for (Entry<String, Long> entry : tableCounts.entrySet()) {
      if (entry.getKey().equals(tableName)) {
        response = restResponseFactory.createTableResponse(entry.getKey(), entry.getValue());
        break;
      }
    }

    if (response == null) {
      throw new FlowableObjectNotFoundException("Could not find a table with name '" + tableName + "'.", String.class);
    }
    return response;
  }
}
