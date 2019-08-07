package plugins

import org.codehaus.groovy.runtime.InvokerHelper
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.maven.Conf2ScopeMappingContainer
import org.gradle.api.artifacts.maven.MavenDeployment
import org.gradle.api.artifacts.maven.MavenResolver
import org.gradle.api.plugins.MavenPluginConvention
import org.gradle.api.plugins.MavenRepositoryHandlerConvention
import org.gradle.api.publication.maven.internal.deployer.MavenRemoteRepository
import org.gradle.api.tasks.Upload
import org.gradle.kotlin.dsl.*
import org.gradle.plugins.signing.Sign
import org.gradle.plugins.signing.SigningExtension
import kotlin.properties.Delegates


/**
 * Configures a Kotlin module for publication.
 *
 */
open class PublishedKotlinModule : Plugin<Project> {

    private fun String.toBooleanOrNull() = listOf(true, false).firstOrNull { it.toString().equals(this, ignoreCase = true) }

    override fun apply(project: Project) {

        project.run {

            plugins.apply("maven")

            val publishedRuntime by configurations.creating {
                the<MavenPluginConvention>()
                    .conf2ScopeMappings
                    .addMapping(0, this, Conf2ScopeMappingContainer.RUNTIME)
            }

            if (!project.hasProperty("prebuiltJar")) {
                plugins.apply("signing")

                val signingRequired = project.findProperty("signingRequired")?.toString()?.toBooleanOrNull()
                    ?: project.property("isSonatypeRelease") as Boolean

                configure<SigningExtension> {
                    isRequired = signingRequired
                    sign(configurations["archives"])
                }

                (tasks.getByName("signArchives") as Sign).apply {
                    enabled = signingRequired
                }
            }

            fun MavenResolver.configurePom() {
                pom.project {
                    withGroovyBuilder {
                        "licenses" {
                            "license" {
                                "name"("The Apache Software License, Version 2.0")
                                "url"("http://www.apache.org/licenses/LICENSE-2.0.txt")
                                "distribution"("repo")
                            }
                        }
                        "name"("${project.group}:${project.name}")
                        "packaging"("jar")
                        // optionally artifactId can be defined here
                        "description"(project.description)
                        "url"("https://kotlinlang.org/")
                        "licenses" {
                            "license" {
                                "name"("The Apache License, Version 2.0")
                                "url"("http://www.apache.org/licenses/LICENSE-2.0.txt")
                            }
                        }
                        "scm" {
                            "url"("https://github.com/JetBrains/kotlin")
                            "connection"("scm:git:https://github.com/JetBrains/kotlin.git")
                            "developerConnection"("scm:git:https://github.com/JetBrains/kotlin.git")
                        }
                        "developers" {
                            "developer" {
                                "name"("Kotlin Team")
                                setProperty("organization", "JetBrains")
                                "organizationUrl"("https://www.jetbrains.com")
                            }
                        }
                    }
                }
                pom.whenConfigured {
                    dependencies.removeIf {
                        InvokerHelper.getMetaClass(it).getProperty(it, "scope") == "test"
                    }

                    val builtinsRemoved = dependencies.removeIf {
                        InvokerHelper.getMetaClass(it).getProperty(it, "groupId") == "org.jetbrains.kotlin"
                                && InvokerHelper.getMetaClass(it).getProperty(it, "artifactId") == "builtins"
                    }

                    if (builtinsRemoved) {
                        logger.warn(
                            "WARNING! Removed dependency on builtins from ${this.artifactId} artifact's maven metadata, " +
                                    "builtins dependency in  ${project.path} project should be 'compileOnly'"
                        )
                    }

                    dependencies
                        .find {
                            InvokerHelper.getMetaClass(it).getProperty(it, "groupId") == "org.jetbrains.kotlin"
                                    && InvokerHelper.getMetaClass(it).getProperty(it, "artifactId") == "kotlin-stdlib"
                        }
                        ?.also {
                            val exclusions = InvokerHelper.getMetaClass(it).getProperty(it, "exclusions") as List<*>
                            if (exclusions.isNotEmpty()) {
                                InvokerHelper.getMetaClass(it).setProperty(it, "exclusions", emptyList<Any>())
                                logger.warn("WARNING! Removed exclusions from kotlin-stdlib dependency of ${this.artifactId} artifact's maven metadata, check kotlin-stdlib dependency of ${project.path} project")
                            }
                        }
                }
            }

            val preparePublication = project.rootProject.tasks.getByName("preparePublication")

            val uploadArchives = (tasks.getByName("uploadArchives") as Upload).apply {

                dependsOn(preparePublication)

                val username: String? by preparePublication.extra
                val password: String? by preparePublication.extra
                val repoUrl: String by preparePublication.extra

                var repository by Delegates.notNull<MavenRemoteRepository>()

                repositories {
                    withConvention(MavenRepositoryHandlerConvention::class) {

                        mavenDeployer {
                            withGroovyBuilder {
                                "beforeDeployment" {
                                    val signing = project.the<SigningExtension>()
                                    if (signing.isRequired)
                                        signing.signPom(delegate as MavenDeployment)
                                }

                                "repository"("url" to repoUrl)!!.also { repository = it as MavenRemoteRepository }.withGroovyBuilder {
                                    if (username != null && password != null) {
                                        "authentication"("userName" to username, "password" to password)
                                    }
                                }
                            }

                            configurePom()
                        }
                    }
                }

                doFirst {
                    repository.url = repoUrl
                }
            }

            tasks.create("publish") {
                dependsOn(uploadArchives)
            }
        }
    }
}
