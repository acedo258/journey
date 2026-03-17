                        _                     _ _ _   
                        | |__   __ _ _ __   __| (_) |_ 
                        | '_ \ / _` | '_ \ / _` | | __|
                        | |_) | (_| | | | | (_| | | |_ 
                        |_.__/ \__,_|_| |_|\__,_|_|\__|
                                                       

                      This is an OverTheWire game server. 
            More information on http://www.overthewire.org/wargames

#To gain access to the next level, you should use the setuid binary in the homedirectory. 
Execute it without arguments to find out how to use it. 
The password for this level can be found in the usual place (/etc/bandit_pass), after you have used the setuid binary.

```
bandit19@bandit:~$ ls -la
total 36
drwxr-xr-x   2 root     root      4096 Oct 14 09:26 .
drwxr-xr-x 150 root     root      4096 Oct 14 09:29 ..
-rwsr-x---   1 bandit20 bandit19 14884 Oct 14 09:26 bandit20-do
-rw-r--r--   1 root     root       220 Mar 31  2024 .bash_logout
-rw-r--r--   1 root     root      3851 Oct 14 09:19 .bashrc
-rw-r--r--   1 root     root       807 Mar 31  2024 .profile
bandit19@bandit:~$ ./bandit20-do 
Run a command as another user.
  Example: ./bandit20-do whoami
bandit19@bandit:~$ ./bandit20-do ls /etc/bandit_pass/
bandit0   bandit13  bandit18  bandit22	bandit27  bandit31  bandit6
bandit1   bandit14  bandit19  bandit23	bandit28  bandit32  bandit7
bandit10  bandit15  bandit2   bandit24	bandit29  bandit33  bandit8
bandit11  bandit16  bandit20  bandit25	bandit3   bandit4   bandit9
bandit12  bandit17  bandit21  bandit26	bandit30  bandit5
bandit19@bandit:~$ ./bandit20-do ls /etc/bandit_pass/bandit20 
/etc/bandit_pass/bandit20
bandit19@bandit:~$ ./bandit20-do cat /etc/bandit_pass/bandit20 
0qXahG8ZjOVMN9Ghs7iOWsCfZyXOUbYO
bandit19@bandit:~$ 
