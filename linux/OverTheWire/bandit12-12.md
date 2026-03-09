
                    _                     _ _ _   
                        | |__   __ _ _ __   __| (_) |_ 
                        | '_ \ / _` | '_ \ / _` | | __|
                        | |_) | (_| | | | | (_| | | |_ 
                        |_.__/ \__,_|_| |_|\__,_|_|\__|
                                                       

                      This is an OverTheWire game server. 
            More information on http://www.overthewire.org/wargames

#The password for the next level is stored in the file data.txt, which is a hexdump of a file that has been repeatedly compressed. For this level it may be useful to create a directory under /tmp in which you can work. Use mkdir with a hard to guess directory name. Or better, use the command “mktemp -d”. Then copy the datafile using cp, and rename it using mv (read the manpages!)

```
bandit12@bandit:~$ ls
data.txt
bandit12@bandit:~$ ls -l
total 4
-rw-r----- 1 bandit13 bandit12 2581 Oct 14 09:26 data.txt
bandit12@bandit:~$ cd /tmp
bandit12@bandit:/tmp$ mktemp -d
/tmp/tmp.utoEDzpRl7
bandit12@bandit:/tmp$ cd /tmp/tmp.utoEDzpRl7
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ cp ~/data.txt .
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ ls
data.txt
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ mv data.txt hexdump_data
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ file hexdump_data 
hexdump_data: ASCII text
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ xxd -r hexdump_data compressed_data
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ ls
compressed_data  hexdump_data
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ cat compressed_data | head
(�hdata2.bin<��BZh91AY&SY�F�-���_������i�������ݵ� xxx
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ mv compressed_data compressed_data.gz
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ ls
compressed_data.gz  hexdump_data
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ gzip -d compressed_data.gz 
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ ls
compressed_data  hexdump_data
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ xxd compressed_data 
00000000: 425a 6839 3141 5926 5359 cc46 b52d 0000  BZh91AY&SY.F.-.. xxx
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ mv compressed_data compressed_data.tar
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ tar -xf compressed_data.tar 
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ ls
compressed_data.tar  data5.bin  hexdump_data
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ tar -xf data5.bin
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ xxd data6.bin 
00000000: 425a 6839 3141 5926 5359 8849 ff13 0000  BZh91AY&SY.I.... xxx
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ ls
compressed_data.tar  data5.bin  data6.bin  hexdump_data
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ tar -xf data6.bin.out
tar: data6.bin.out: Cannot open: No such file or directory
tar: Error is not recoverable: exiting now
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ bzip2 -d data6.bin
bzip2: Can't guess original name for data6.bin -- using data6.bin.out
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ ls
compressed_data.tar  data5.bin  data6.bin.out  hexdump_data
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ tar -xf data6.bin.out
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ ls
compressed_data.tar  data5.bin  data6.bin.out  data8.bin  hexdump_data
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ xxd data8.bin
00000000: 1f8b 0808 2817 ee68 0203 6461 7461 392e  ....(..h..data9. xxx
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ mv data8.bin data8.gz
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ gzip -d data8.gz
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ ls
compressed_data.tar  data5.bin  data6.bin.out  data8  hexdump_data
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ cat data8
The password is FO5dwFsc0cbaIiH0h8J2eUks2vdTDwAn
bandit12@bandit:/tmp/tmp.utoEDzpRl7$ 

```

