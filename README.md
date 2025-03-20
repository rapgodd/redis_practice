# Cluster 레플리카 노드 백업 테스트

### 1. 6개 레디스 컴포즈파일로 생성

### 2. Redis node 1번에 접속 그리고 클러스터 설정 후 레플리카 비율 설정

```c

클러스터 설정 이후 레플리카 비율 설정 명령어 :
docker exec -it src-redis-node-1-1 bash
redis-cli --cluster create \
src-redis-node-1-1:6379 src-redis-node-2-1:6379 src-redis-node-3-1:6379 \
src-redis-node-4-1:6379 src-redis-node-5-1:6379 src-redis-node-6-1:6379 \
--cluster-replicas 1

```

### 3. 마스터 레디스 컨테이너 강제종료 후 백업 잘 되는지 테스트.

   
   <img width="883" alt="스크린샷 2025-03-18 오후 8 01 43" src="https://github.com/user-attachments/assets/6c742e3b-0a04-4c2c-802a-863b1d2e285a" /></br>


  
### 4. 해당 마스터의 레플리카 만들기.</br>


  - **4.1. 먼저 레디스 컨테이너를 클러스터 설정을 해서 하나 생성**</br>
   
   
   
   <img width="327" alt="redis-node-7" src="https://github.com/user-attachments/assets/4e1baeca-405a-45a1-9edd-24ce3bb5256a" /></br>
   

   - **4.2. 해당 노드를 새로운 마스터와 연결**</br>
   
   
   ```c
        redis-cli --cluster add-node <새노드_IP:PORT> <어느_정상_노드_IP:PORT> \
            --cluster-slave \
            --cluster-master-id <붙을_마스터_ID>
   ```
</br>

### 결과
<img width="1099" alt="스크린샷 2025-03-18 오후 9 56 27" src="https://github.com/user-attachments/assets/e5171b6d-a92f-4c2c-8814-656aeefce3ba" /></br></br></br>

----
</br></br></br>

## 새로운 마스터 노드를 트래픽 분산을 위해 추가[**샤딩**]</br></br>

#### 1. 레디스 컨테이너 하나 생성</br>

```c
docker run -d --name redis-node-7 --net redis-cluster -p 7007:6379 redis —cluster-enabled yes
```

#### 2. 새로 생성한 노드를 클러스터에 추가</br>

```c
명령어
redis-cli --cluster add-node [새로 추가한 레디스 컨테이너 이름]:[포트번호] [원래 클러스터에 있는 샤드 이름(컨테이너 이름)]:[포트 번호]

예시
redis-cli --cluster add-node src-redis-node-8-1:6379 src-redis-node-7-1:6379
```


<img width="834" alt="스크린샷 2025-03-18 오후 10 43 18" src="https://github.com/user-attachments/assets/414988ba-1b56-41ea-9bd9-dbd73efec3b7" /></br></br>

#### 3. 해당 노드에 리밸런싱을 하도록 명령어를 작성</br>

```c
명령어
- redis-cli --cluster rebalance [컨테이너 이름]:[포트 번호] --cluster-use-empty-masters

예시
- redis-cli --cluster rebalance src-redis-node-7-1:6379 --cluster-use-empty-masters
```

<img width="1167" alt="스크린샷 2025-03-18 오후 10 57 27" src="https://github.com/user-attachments/assets/58ce320b-ebe3-42c3-a813-236d911f8b67" /></br>

## 결과

<img width="1173" alt="스크린샷 2025-03-19 오전 12 45 45" src="https://github.com/user-attachments/assets/e2597c61-4087-43af-9db6-3886779b8321" /></br>

---
</br></br></br>

# 참고 명령어

```c
참고 : cluster nodes
      - 클러스터 안 노드의 수와 노드의 역할 보여줌
```

```c
참고 : redis-cli -c
      - 클러스터 모드로 cli를 바꿔줌.
        해당 모드로 set a b를 하면 key값 노드로 리다이렉트
```

<img width="636" alt="스크린샷 2025-03-18 오후 10 05 52" src="https://github.com/user-attachments/assets/dd3ee6c4-66c5-4799-9426-c7f035ca0579" /></br>

```c
참고 : docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' 컨테이너 Id
      - 컨테이너 IP주소 받기
```

```c
참고 : cluster keyslot key
      - "키의 해시함수 적용 % 16384" 한 값 출력.
```
