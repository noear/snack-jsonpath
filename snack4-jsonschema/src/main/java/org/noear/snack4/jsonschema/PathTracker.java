/*
 * Copyright 2005-2025 noear.org and authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.noear.snack4.jsonschema;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * JSON路径追踪器，用于记录当前JSON节点的访问路径
 *
 * @author noear
 * @since 4.0
 */
public class PathTracker {
    private final StringBuilder pathBuilder;
    private String wildcardIndexPath;
    private final Deque<Integer> lengthStack = new ArrayDeque<>();

    public PathTracker() {
        this.pathBuilder = new StringBuilder("$");
        this.lengthStack.push(pathBuilder.length());
    }

    public PathTracker(String rootPath) {
        this.pathBuilder = new StringBuilder(rootPath);
        this.lengthStack.push(pathBuilder.length());
    }

    /** 进入对象属性 */
    public void enterProperty(String property) {
        pathBuilder.append('.').append(property);
        lengthStack.push(pathBuilder.length());
    }

    /** 进入数组索引 */
    public void enterIndex(int arrayIndex) {
        wildcardIndexPath = pathBuilder.toString()+"[*]";
        pathBuilder.append('[').append(arrayIndex).append(']');
        lengthStack.push(pathBuilder.length());
    }

    /** 获取当前路径 */
    public String currentPath() {
        return pathBuilder.toString();
    }

    /** 退出当前层级 */
    public void exit() {
        wildcardIndexPath = null;

        if (lengthStack.size() > 1) {
            lengthStack.pop(); // 弹出当前长度
            pathBuilder.setLength(lengthStack.peek()); // 恢复到上一个长度
        }
    }

    /** 创建新实例 */
    public static PathTracker begin() {
        return new PathTracker();
    }

    public String getWildcardPath() {
        return wildcardIndexPath;
    }
}