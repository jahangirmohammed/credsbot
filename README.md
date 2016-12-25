# credsbot

CredsBot is a slack bot.

CredsBot uses -

1. Slack Scala Client(https://github.com/gilbertw1/slack-scala-client) to interact with Slack.
2. Vault(https://www.vaultproject.io/intro/index.html) to securely deal with site details(like user name, password).

Usage:

1. Install Vault(https://www.vaultproject.io/intro/index.html) on a server.
2. Add bot(https://api.slack.com/bot-users) to Slack. 
3. a. git clone https://github.com/jahangirmohammed/credsbot.git.
   b. Create a file called all.creds like example.creds(https://github.com/jahangirmohammed/credsbot/blob/master/src/main/resources/example.creds) 
   in src/main/resources.
   c. In all.creds file, Add API token of the bot created and Vault's IP address, root_token.