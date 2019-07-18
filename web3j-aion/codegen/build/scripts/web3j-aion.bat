@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      http://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  web3j-aion startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and WEB3J_AION_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\web3j-aion-codegen-0.1.0-SNAPSHOT.jar;%APP_HOME%\lib\web3j-aion-common-0.1.0-SNAPSHOT.jar;%APP_HOME%\lib\bcpkix-jdk15on-1.62.jar;%APP_HOME%\lib\kotlin-stdlib-jdk8-1.3.41.jar;%APP_HOME%\lib\kotlin-reflect-1.3.41.jar;%APP_HOME%\lib\kotlin-noarg-1.3.41.jar;%APP_HOME%\lib\kotlin-native-utils-1.3.41.jar;%APP_HOME%\lib\kotlin-logging-1.6.10.jar;%APP_HOME%\lib\ktlint-0.31.0.jar;%APP_HOME%\lib\codegen-4.4.0-SNAPSHOT.jar;%APP_HOME%\lib\core-4.4.0-SNAPSHOT.jar;%APP_HOME%\lib\picocli-3.0.0.jar;%APP_HOME%\lib\logmgr-logback-1.0.2.jar;%APP_HOME%\lib\abi-4.4.0-SNAPSHOT.jar;%APP_HOME%\lib\crypto-4.4.0-SNAPSHOT.jar;%APP_HOME%\lib\rlp-4.4.0-SNAPSHOT.jar;%APP_HOME%\lib\utils-4.4.0-SNAPSHOT.jar;%APP_HOME%\lib\bcprov-jdk15on-1.62.jar;%APP_HOME%\lib\rlp4j-46e0cb3.jar;%APP_HOME%\lib\util4j-2c5aa4ad.jar;%APP_HOME%\lib\kotlin-stdlib-jdk7-1.3.41.jar;%APP_HOME%\lib\kotlin-gradle-plugin-api-1.3.41.jar;%APP_HOME%\lib\kotlin-gradle-plugin-model-1.3.41.jar;%APP_HOME%\lib\kotlin-stdlib-1.3.41.jar;%APP_HOME%\lib\kotlin-logging-common-1.6.10.jar;%APP_HOME%\lib\logback-classic-1.1.2.jar;%APP_HOME%\lib\slf4j-api-1.7.25.jar;%APP_HOME%\lib\ktlint-core-0.31.0.jar;%APP_HOME%\lib\ktlint-ruleset-standard-0.31.0.jar;%APP_HOME%\lib\ktlint-ruleset-experimental-0.31.0.jar;%APP_HOME%\lib\ktlint-reporter-plain-0.31.0.jar;%APP_HOME%\lib\ktlint-reporter-json-0.31.0.jar;%APP_HOME%\lib\ktlint-reporter-checkstyle-0.31.0.jar;%APP_HOME%\lib\ktlint-test-0.31.0.jar;%APP_HOME%\lib\klob-0.2.1.jar;%APP_HOME%\lib\maven-aether-provider-3.3.9.jar;%APP_HOME%\lib\maven-model-builder-3.3.9.jar;%APP_HOME%\lib\maven-model-3.3.9.jar;%APP_HOME%\lib\maven-repository-metadata-3.3.9.jar;%APP_HOME%\lib\guava-18.0.jar;%APP_HOME%\lib\aether-impl-1.1.0.jar;%APP_HOME%\lib\aether-connector-basic-1.1.0.jar;%APP_HOME%\lib\aether-transport-file-1.1.0.jar;%APP_HOME%\lib\aether-transport-http-1.1.0.jar;%APP_HOME%\lib\aether-spi-1.1.0.jar;%APP_HOME%\lib\aether-util-1.1.0.jar;%APP_HOME%\lib\aether-api-1.1.0.jar;%APP_HOME%\lib\tuples-4.4.0-SNAPSHOT.jar;%APP_HOME%\lib\jnr-unixsocket-0.21.jar;%APP_HOME%\lib\logging-interceptor-3.8.1.jar;%APP_HOME%\lib\okhttp-3.8.1.jar;%APP_HOME%\lib\rxjava-2.2.2.jar;%APP_HOME%\lib\Java-WebSocket-1.3.8.jar;%APP_HOME%\lib\jackson-databind-2.8.5.jar;%APP_HOME%\lib\javapoet-1.7.0.jar;%APP_HOME%\lib\kotlin-stdlib-common-1.3.41.jar;%APP_HOME%\lib\annotations-13.0.jar;%APP_HOME%\lib\assertj-core-3.9.0.jar;%APP_HOME%\lib\plexus-component-annotations-1.6.jar;%APP_HOME%\lib\maven-artifact-3.3.9.jar;%APP_HOME%\lib\maven-builder-support-3.3.9.jar;%APP_HOME%\lib\plexus-utils-3.0.22.jar;%APP_HOME%\lib\commons-lang3-3.4.jar;%APP_HOME%\lib\plexus-interpolation-1.21.jar;%APP_HOME%\lib\httpclient-4.3.5.jar;%APP_HOME%\lib\jnr-enxio-0.19.jar;%APP_HOME%\lib\jnr-posix-3.0.47.jar;%APP_HOME%\lib\jnr-ffi-2.1.9.jar;%APP_HOME%\lib\jnr-constants-0.9.11.jar;%APP_HOME%\lib\okio-1.13.0.jar;%APP_HOME%\lib\reactive-streams-1.0.2.jar;%APP_HOME%\lib\jackson-annotations-2.8.0.jar;%APP_HOME%\lib\jackson-core-2.8.5.jar;%APP_HOME%\lib\logback-core-1.1.2.jar;%APP_HOME%\lib\httpcore-4.3.2.jar;%APP_HOME%\lib\commons-codec-1.6.jar;%APP_HOME%\lib\jffi-1.2.17.jar;%APP_HOME%\lib\jffi-1.2.17-native.jar;%APP_HOME%\lib\asm-commons-5.0.3.jar;%APP_HOME%\lib\asm-analysis-5.0.3.jar;%APP_HOME%\lib\asm-util-5.0.3.jar;%APP_HOME%\lib\asm-tree-5.0.3.jar;%APP_HOME%\lib\asm-5.0.3.jar;%APP_HOME%\lib\jnr-a64asm-1.0.0.jar;%APP_HOME%\lib\jnr-x86asm-1.0.2.jar

@rem Execute web3j-aion
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %WEB3J_AION_OPTS%  -classpath "%CLASSPATH%" org.web3j.aion.codegen.AionGeneratorMain %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable WEB3J_AION_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%WEB3J_AION_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
