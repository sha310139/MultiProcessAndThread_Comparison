# MultiProcessAndThread_Comparison
使用MultiProcess及MultiThread分別執行BubbleSort及MergeSort，<br>
比較不同個數的Process和Thread在不同資料量(1、10、50、100萬)執行速度的差異。<br>
<br>

## 開發平台
Win10
<br>
<br>

## 使用開發環境
Eclipse Java 2019-03<br>
java version "1.8.0_212"<br>
Java(TM) SE Runtime Environment (build 1.8.0_212-b10)<br>
Java HotSpot(TM) 64-Bit Server VM (build 25.212-b10, mixed mode)<br>
<br>
<br>

## 使用方式
預設的讀檔位置會在classpath
在程式一開始會顯示
將input檔放至classpath(通常如下)

     ./process_thread/bin/processthread/
     
編譯並執行

    ./process_thread/bin/processthread/Command.class


輸入input檔名後(限制使用.txt檔)<br>
程式會讀取指定檔案中的第一個數字，來決定要執行哪個任務<br>
執行完畢後會輸出output的txt檔，將排序好的內容及所花費時間寫入<br>
<br> 
<br>


## input檔格式
第一行為要執行的任務數字<br>
第二行之後為要排序的N個數字<br>
<br>
<br>

## output檔格式
包含排序後的數字以及所花費時間<br>
<br>
<br>

## 規則細節說明

設有一個檔案內有若干個(N個)數目字(至少多於1萬,最多不超過100萬)<br>
此程式能夠將該些數目字切成k份(k由使用者自訂)<br>
並由K個threads分別進行Bubble Sort之後<br>
再由k-1個threads作Merge Sort<br>
以完成這些數目字之排序<br>
同時顯示CPU執行之時間<br>
<br>

此程式完成以下四個任務 :<br>

    1. 將N個數目字直接作Bubble Sort,並顯示CPU執行之時間.
    2. 將N個數目字切成k份,並由K個threads分別進行Bubble  Sort之後,再由k-1個threads作Merge Sort,同時顯示CPU執行之時間.
    3. 將N個數目字切成k份,並由K個Processes分別進行Bubble  Sort之後,再由k-1個Processes作Merge Sort,同時顯示CPU執行之時間.
    4. 將N個數目字切成k份,在一個Process內對K份資料進行Bubble  Sort之後,再用同一個Process作Merge Sort,同時顯示CPU執行之時間.
    
    
 ## 執行結果分析
 
#### 比較四種任務耗費的時間，探討使用Thread後，資料筆數在1萬、10萬、50萬、100萬，執行速度的差別

任務1 :<br>

    1W 0s
    10W 58s
    50W 1970s
    100W 6970s

任務2 (k = 10) :<br>

    1W 0s
    10W 12s
    50W 168s
    100W 612s

任務3 (k = 10) :<br>

    1W 5s
    10W 13s
    50W 140s
    100W 569s

任務4 (k = 10) :<br>

    1W 0s
    10W 7s
    50W 161s
    100W 632s



![]()

從圖表中可以看到，任務一沒有做切割，也沒有使用thread及process，<br>
明顯花了比其他任務還要更長的時間，特別是10W之後會有明顯差異<br>
<br>
<br>

![]()

從圖表中可以看到，任務三執行的時間比任務二及任務四還快，<br>
原因是使用多個process，process光一個就能獨佔一次的CPU time slice，<br>
若k=10，就有10個process在競爭CPU資源，使用到CPU的次數及時間相對較多<br>
<br>
而同樣使用切割，任務二使用user thread是共用同一個process的time slice，<br>
因此所獲得的時間相對比多個processes的還要少<br>
<br>
而任務四只有使用一個process，比任務二還要久是因為，<br>
在程式設計上中間還要經過傳參數、重新讀檔寫檔等等瑣碎過程，<br>
但其實時間並沒有相差太大<br>
<br>
速度快慢在50W之後才有明顯差異，<br>
10W之前不管使用哪一個，速度差不多<br>
<br>
<br>

#### 總結

thread在共享資料這方面很有利，不需要花太多時間去傳遞資料，<br>
但輪到CPU時所有thread得共享同一個time slice<br>
<br>
process方面，在process夠多時，能輪到CPU的機會相對更多，<br>
一個Process就能獨享一個time slice，但必須花費傳遞資料的時間，<br>
特別是礙於java的寫法，必須使用寫檔方式傳遞資料的話，切太多process反而增加更多寫檔時間，<br>
比使用thread更慢，資料量少的時候，使用process也是反效果<br>

