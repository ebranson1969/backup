start cmd /k call backupjava.bat

java -Xms4096M -Xmx8192M -Xmn512M -d64 -jar forge-1.12-14.21.1.2415-universal.jar

copy /y NUL kill >NUL