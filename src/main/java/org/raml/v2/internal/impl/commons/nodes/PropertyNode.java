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
package org.raml.v2.internal.impl.commons.nodes;

import javax.annotation.Nonnull;

import org.raml.v2.internal.impl.v10.nodes.types.builtin.TypeNode;
import org.raml.v2.internal.framework.nodes.KeyValueNodeImpl;
import org.raml.v2.internal.framework.nodes.Node;
import org.raml.v2.internal.framework.nodes.ReferenceNode;
import org.raml.v2.internal.framework.nodes.StringNode;
import org.raml.v2.internal.utils.NodeUtils;

public class PropertyNode extends KeyValueNodeImpl
{

    public PropertyNode()
    {
    }

    public PropertyNode(PropertyNode node)
    {
        super(node);
    }

    public String getName()
    {
        final StringNode key = (StringNode) getKey();
        String keyValue = key.getValue();
        return keyValue.endsWith("?") ? keyValue.substring(0, keyValue.length() - 1) : keyValue;
    }

    public boolean isRequired()
    {
        final StringNode key = (StringNode) getKey();
        return !key.getValue().endsWith("?");
    }

    public TypeNode getTypeNode()
    {
        Node value = getValue();
        if (value instanceof TypeNode)
        {
            return (TypeNode) value;
        }
        else if (value instanceof ReferenceNode)
        {
            return (TypeNode) ((ReferenceNode) value).getRefNode();
        }
        else
        {
            throw new IllegalStateException("Value should be a TypeNode or a Reference to a TypeNode");
        }
    }

    @Nonnull
    @Override
    public Node copy()
    {
        return new PropertyNode(this);
    }

    @Override
    public String toString()
    {
        return this.getName() + ":" + NodeUtils.getType(this.getValue());
    }

}