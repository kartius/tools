To install redis follow these steps:

Set up a non-root user with sudo privileges

Install build and test dependencies: sudo apt update sudo apt full-upgrade sudo apt install build-essential tcl

Set up redis:

Download latest copy via this link or with this curl -O http://download.redis.io/redis-stable.tar.gz
Create a temporary folder for it in say your /home/username/redis-stable directory
Move into created folder and extract it tar xzvf redis-stable.tar.gz
Change into the folder cd redis-stable and build it with make make test sudo make install
Configure redis:

Create configuration directory: sudo mkdir /etc/redis

Move sample redis configuration file: sudo cp /home/george/redis-stable/redis.conf /etc/redis

Edit the file: sudo nano /etc/redis/redis.conf # or with any other text editor

Make two changes there: supervised no to supervised systemd dir to dir /var/lib/redis # for persistent data dump

Set up the systemd unit file: sudo nano /etc/systemd/system/redis.service Add the text: [Unit] Description=Redis In-Memory Data Store After=network.target

[Service] User=redis Group=redis ExecStart=/usr/local/bin/redis-server /etc/redis/redis.conf ExecStop=/usr/local/bin/redis-cli shutdown Restart=always

[Install] WantedBy=multi-user.target 6. Set up redis user, groups and directories:

create redis user and group with same ID but no home directory: sudo adduser --system --group --no-create-home redis
sudo mkdir /var/lib/redis # create directory sudo chown redis:redis /var/lib/redis # make redis own /var/lib/redis sudo chmod 770 /var/lib/redis # adjust permission

Test redis:
Start redis service: sudo systemctl start redis
Check status: systemctl status redis
Result of status if started successfully: Output ● redis.service - Redis Server Loaded: loaded (/etc/systemd/system/redis.service; enabled; vendor preset: enabled) Active: active (running) since Wed 2016-05-11 14:38:08 EDT; 1min 43s ago Process: 3115 ExecStop=/usr/local/bin/redis-cli shutdown (code=exited, status=0/SUCCESS) Main PID: 3124 (redis-server) Tasks: 3 (limit: 512) Memory: 864.0K CPU: 179ms CGroup: /system.slice/redis.service └─3124 /usr/local/bin/redis-server 127.0.0.1:6379

Or use docker:

docker run -p 6379:6379 redis:latest


Test instance:

Connect: redis-cli

Test connectivity at prompt: 127.0.0.1:6379> ping # result PONG

Check ability to set keys: 127.0.0.1:6379 set test "It's working!" # result ok

Get the key just set: 127.0.0.1:6379 get test # result "It's working!"

Exit redis: 127.0.0.1:6379 exit

Restart redis and then re-run steps 1, 4, and 5:

sudo systemctl restart redis 7. Enable redis to start at boot: sudo systemctl enable redis

Source:

Digital Ocean - how to install and configure redis on Ubuntu 16.04

Direct download links

Check redis data:

redis-cli
scan 0
Get data by key

get [key]