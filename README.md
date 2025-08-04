# browserstack-selenium-load-testing-sample

![BrowserStack Logo](https://d98b8t1nnulk5.cloudfront.net/production/images/layout/logo-header.png?1469004780)

## Getting Started

### Run Sample Build

1. **Clone the repository**

   ```sh
   git clone https://github.com/browserstack/browserstack-playwright-load-testing-sample.git
   cd testng-browserstack
   ```

2. **Install Maven dependencies**

   ```sh
   mvn compile
   ```

3. **Install BrowserStack CLI**

   Download the appropriate BrowserStack CLI binary based on your operating system:

   - **macOS x86**  
     [browserstack-cli-macOS-x86](https://load-api.browserstack.com/api/v1/binary?os=macos&arch=x64)

   - **macOS ARM**  
     [browserstack-cli-macOS-arm](https://load-api.browserstack.com/api/v1/binary?os=macos&arch=arm64)

   - **Windows x86**  
     [browserstack-cli-windows](https://load-api.browserstack.com/api/v1/binary?os=win&arch=x64)

   - **Linux x86**  
     [browserstack-cli-linux-x86](https://load-api.browserstack.com/api/v1/binary?os=linux&arch=arm64)

   - **Linux ARM**  
     [browserstack-cli-linux-arm](https://load-api.browserstack.com/api/v1/binary?os=linux&arch=x64)

   > Place the downloaded `browserstack-cli` binary in the root of your project.

4. **Run tests using BrowserStack CLI**

   ```sh
   ./browserstack-cli load run
   ```

5. **View Test Results**

   Visit the [BrowserStack Load-Testing Dashboard](https://load.browserstack.com/projects) to monitor and analyze your test runs.

---