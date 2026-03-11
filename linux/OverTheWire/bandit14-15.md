                        _                     _ _ _   
                        | |__   __ _ _ __   __| (_) |_ 
                        | '_ \ / _` | '_ \ / _` | | __|
                        | |_) | (_| | | | | (_| | | |_ 
                        |_.__/ \__,_|_| |_|\__,_|_|\__|
                                                       

                      This is an OverTheWire game server. 
            More information on http://www.overthewire.org/wargames

#The password for the next level can be retrieved by submitting the password of the current level to port 30000 on localhost.

```
bandit14@bandit:/$ cd /
behemoth/           krypton/            media/              snap/
bin/                lib/                mnt/                srv/
bin.usr-is-merged/  lib32/              narnia/             sys/
boot/               lib64/              opt/                tmp/
dev/                lib.usr-is-merged/  proc/               usr/
drifter/            libx32/             root/               utumno/
etc/                lost+found/         run/                var/
formulaone/         manpage/            sbin/               vortex/
home/               maze/               sbin.usr-is-merged/ 
bandit14@bandit:/$ cd /

bandit14@bandit:~$ cd /etc/b
bandit_pass/       behemoth_pass/     byobu/             
bash_completion.d/ binfmt.d/          
bandit14@bandit:~$ cd /etc/bandit_pass
bandit14@bandit:/etc/bandit_pass$ ls
bandit0   bandit12  bandit16  bandit2   bandit23  bandit27  bandit30  bandit4  bandit8
bandit1   bandit13  bandit17  bandit20  bandit24  bandit28  bandit31  bandit5  bandit9
bandit10  bandit14  bandit18  bandit21  bandit25  bandit29  bandit32  bandit6
bandit11  bandit15  bandit19  bandit22  bandit26  bandit3   bandit33  bandit7

bandit14@bandit:~$ cat /etc/bandit_pass/bandit14
MU4VWeTyJk8ROof1qqmcBPaLh7lDCPvS
bandit14@bandit:~$ nc localhost 30000
MU4VWeTyJk8ROof1qqmcBPaLh7lDCPvS
Correct!
8xCjnmgoKbGLhHFAZlGE5Tmu4M2tKJQo
```

