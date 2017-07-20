/*
 * Copyright 2017 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.services.query.app;

import com.querydsl.core.types.Predicate;
import org.activiti.services.query.app.assembler.ProcessInstanceQueryResourceAssembler;
import org.activiti.services.query.app.model.ProcessInstance;
import org.activiti.services.query.app.resources.ProcessInstanceQueryResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/query/processinstances", produces = MediaTypes.HAL_JSON_VALUE)
public class ProcessInstanceQueryController {

    @Autowired
    private ProcessInstanceQueryRestResource dao;

    @Autowired
    private ProcessInstanceQueryResourceAssembler resourceAssembler;

    @RequestMapping(method = RequestMethod.GET, value = "")
    @ResponseBody
    public PagedResources<ProcessInstanceQueryResource> findAllByWebQuerydsl(
            @QuerydslPredicate(root = ProcessInstance.class) Predicate predicate, Pageable pageable, PagedResourcesAssembler<ProcessInstance> pagedResourcesAssembler) {
        return pagedResourcesAssembler.toResource(dao.findAll(predicate,pageable), resourceAssembler);
    }

    //TODO: implement for a single task using findOne and include links from the findAll to individual records like runtime does - see TaskResourceAssembler

}
