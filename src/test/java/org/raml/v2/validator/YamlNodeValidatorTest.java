/*
 * Copyright 2013 (c) MuleSoft, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package org.raml.v2.validator;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.raml.v2.internal.impl.RamlBuilder;
import org.raml.v2.internal.impl.commons.nodes.PayloadValidationResultNode;
import org.raml.v2.internal.impl.commons.nodes.ResourceNode;
import org.raml.v2.internal.impl.v10.nodes.types.builtin.UnionTypeNode;
import org.raml.v2.internal.framework.nodes.Node;
import org.raml.v2.internal.utils.NodeValidator;

public class YamlNodeValidatorTest
{
    private NodeValidator nodeValidator;
    private Node tree;
    private Node type;

    @Before
    public void setUp() throws IOException
    {
        RamlBuilder builder = new RamlBuilder();
        tree = builder.build(new File(this.getClass().getClassLoader().getResource("org/raml/v2/parser/examples/madness/input.raml").getPath()));
        this.nodeValidator = new NodeValidator(builder.getResourceLoader());
        this.type = tree.findDescendantsWith(ResourceNode.class).get(0).findDescendantsWith(UnionTypeNode.class).get(0);
    }

    @Test
    public void testParsingFailure()
    {
        PayloadValidationResultNode validationNode =
                this.nodeValidator.validatePayload(type, "{\"discriminator\":\"HasHome Parrot\", \"livesInside\": \"sometimes\", \"feathers\": \"colorful\"}");
        Assert.assertFalse(validationNode.validationSucceeded());
    }

    @Test
    public void testParsingOk()
    {
        PayloadValidationResultNode validationNode =
                this.nodeValidator.validatePayload(type, "{\"discriminator\":\"HasHome Parrot\", \"livesInside\": true, \"feathers\": \"blue\"}");
        Assert.assertTrue(validationNode.validationSucceeded());
    }
}