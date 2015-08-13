/*
 * Copyright 2010-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.idea.completion.test;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.JUnit3RunnerWithInners;
import org.jetbrains.kotlin.test.JetTestUtils;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.TestsPackage}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("idea/idea-completion/testData/basic/custom")
@TestDataPath("$PROJECT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
public class JvmWithLibBasicCompletionTestGenerated extends AbstractJvmWithLibBasicCompletionTest {
    public void testAllFilesPresentInCustom() throws Exception {
        JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/idea-completion/testData/basic/custom"), Pattern.compile("^(.+)\\.kt$"), false);
    }

    @TestMetadata("NamedArgumentsJava.kt")
    public void testNamedArgumentsJava() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/idea-completion/testData/basic/custom/NamedArgumentsJava.kt");
        doTest(fileName);
    }

    @TestMetadata("NamedArgumentsKotlin.kt")
    public void testNamedArgumentsKotlin() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/idea-completion/testData/basic/custom/NamedArgumentsKotlin.kt");
        doTest(fileName);
    }

    @TestMetadata("SamAdapter.kt")
    public void testSamAdapter() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/idea-completion/testData/basic/custom/SamAdapter.kt");
        doTest(fileName);
    }

    @TestMetadata("SamAdapterAndGenerics.kt")
    public void testSamAdapterAndGenerics() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/idea-completion/testData/basic/custom/SamAdapterAndGenerics.kt");
        doTest(fileName);
    }

    @TestMetadata("TopLevelNonImportedExtFun.kt")
    public void testTopLevelNonImportedExtFun() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/idea-completion/testData/basic/custom/TopLevelNonImportedExtFun.kt");
        doTest(fileName);
    }

    @TestMetadata("TopLevelNonImportedExtProp.kt")
    public void testTopLevelNonImportedExtProp() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/idea-completion/testData/basic/custom/TopLevelNonImportedExtProp.kt");
        doTest(fileName);
    }

    @TestMetadata("TopLevelNonImportedFun.kt")
    public void testTopLevelNonImportedFun() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/idea-completion/testData/basic/custom/TopLevelNonImportedFun.kt");
        doTest(fileName);
    }

    @TestMetadata("TopLevelNonImportedProperty.kt")
    public void testTopLevelNonImportedProperty() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/idea-completion/testData/basic/custom/TopLevelNonImportedProperty.kt");
        doTest(fileName);
    }
}
