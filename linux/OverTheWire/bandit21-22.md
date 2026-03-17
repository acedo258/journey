                        _                     _ _ _   
                        | |__   __ _ _ __   __| (_) |_ 
                        | '_ \ / _` | '_ \ / _` | | __|
                        | |_) | (_| | | | | (_| | | |_ 
                        |_.__/ \__,_|_| |_|\__,_|_|\__|
                                                       

                      This is an OverTheWire game server. 
            More information on http://www.overthewire.org/wargames

#A program is running automatically at regular intervals from cron, the time-based job scheduler. 
Look in /etc/cron.d/ for the configuration and see what command is being executed.

```
bandit21@bandit:~$ ls -la /etc/cron.d
total 60
drwxr-xr-x   2 root root  4096 Oct 14 09:29 .
drwxr-xr-x 128 root root 12288 Oct 27 05:55 ..
-r--r-----   1 root root    47 Oct 14 09:26 behemoth4_cleanup
-rw-r--r--   1 root root   123 Oct 14 09:19 clean_tmp
-rw-r--r--   1 root root   120 Oct 14 09:26 cronjob_bandit22
-rw-r--r--   1 root root   122 Oct 14 09:26 cronjob_bandit23
-rw-r--r--   1 root root   120 Oct 14 09:26 cronjob_bandit24
-rw-r--r--   1 root root   201 Apr  8  2024 e2scrub_all
-r--r-----   1 root root    48 Oct 14 09:27 leviathan5_cleanup
-rw-------   1 root root   138 Oct 14 09:28 manpage3_resetpw_job
-rwx------   1 root root    52 Oct 14 09:29 otw-tmp-dir
-rw-r--r--   1 root root   102 Mar 31  2024 .placeholder
-rw-r--r--   1 root root   396 Jan  9  2024 sysstat
bandit21@bandit:~$ cat /etc/cron.d/cronjob_bandit22
@reboot bandit22 /usr/bin/cronjob_bandit22.sh &> /dev/null
* * * * * bandit22 /usr/bin/cronjob_bandit22.sh &> /dev/null
bandit21@bandit:~$ cat /usr/bin/cronjob_bandit22.sh
#!/bin/bash
chmod 644 /tmp/t7O6lds9S0RqQh9aMcz6ShpAoZKF7fgv
cat /etc/bandit_pass/bandit22 > /tmp/t7O6lds9S0RqQh9aMcz6ShpAoZKF7fgv
bandit21@bandit:~$ cat /tmp/t7O6lds9S0RqQh9aMcz6ShpAoZKF7fgv
tRae0UfB9v0UzbCdn9cY0gQnds9GF58Q
