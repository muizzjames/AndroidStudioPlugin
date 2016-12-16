This project is source for Android Studio IDE Plugin Library to develop.
IDE: Intellij IDEA
1. 설치방법
- Android Studio를 실행한다
- File안내에서 Settings을 선택한다.
- Settings창에서 왼쪽창에서 plugins을 선택한다.
- 화면아래에 있는 Install plugin from disk...단추를 눌러 개발한 plugin jar파일을 설치한다.
- file선택창에서 파일을 선택한다.
- OK 또는 Apply단추를 눌러 설치를 완료한다.
주의: 오른쪽창의 설치된 plugin 목록에서 능동상태에 있는가를 확인한다.
IDE를 재실행한다.
plugin icon이나 창이 IDE에 나타나는가를 확인한다.
만일 plugin project에 오유가 있다면 IDE가 실행되지 못할수 있다. 이때에는 실행중지창에서 settings, plugin을 선택하여 우의 plugin을 해제(uninstall)하면 된다.

2. 개발방법
- 개발도구:Intellij IDEA이다.
- New Project에서 plugin을 선택한다.
- 기본적인 개발방법은 Eclipse, Android Studio와 비슷하다.
- 차이점: SDK를 선택해주어야 하는데 JDK1.8과 Intellij IDEA를 함께 해주어야 한다.(File/Project Structure/SDKs/+)
외부서고는 File/Project Structure/Libraries/+에서 진행한다.
- 결과처리: Build/Prepare Plugin Module...하면 *.jar을 작성하고 zip파일로 만든다.
이 *.jar파일을 리용한다.

Testing can do at Intellij IDEA.