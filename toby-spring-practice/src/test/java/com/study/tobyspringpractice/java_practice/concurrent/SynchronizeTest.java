package com.study.tobyspringpractice.java_practice.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SynchronizeTest {
    static class WebSite implements Runnable {
        private final String webSiteName;
        private final WebBrowser webBrowser;

        public WebSite(final String webSiteName, final WebBrowser webBrowser) {
            this.webSiteName = webSiteName;
            this.webBrowser = webBrowser;
        }

        @Override
        public void run() {
            // 메서드 블럭 동기화
            synchronized (this) {
                while (true) {
                    try {
                        Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    webBrowser.createNewTab(webSiteName);
                }
            }

        }
    }

    static class WebBrowser {
        private static final String SPACE = " ";

        private final List<String> webSiteNames = new ArrayList<>();
        private final List<Integer> webSiteIndexes = new ArrayList<>();
        private final int maxWebCount;
        private int webSiteIndex = 0;

        public WebBrowser(final int maxWebCount) {
            this.maxWebCount = maxWebCount;
        }

        public synchronized void createNewTab(final String webSiteName) {
            try {
                if (full()) {
                    wait();
                }
                System.out.println(webSiteName + " 사이트가 켜지는 중입니다...");
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
                webSiteNames.add(webSiteName);
                webSiteIndexes.add(++webSiteIndex);
                showRunningBrowser();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void showRunningBrowser() {
            if (webSiteNames.size() > maxWebCount) {
                throw new UnsupportedOperationException("현재 브라우저 탭이 6개 이상 활성화 되어 강제 종료합니다...");
            }

            System.out.println("┌───────────────────────────────────────────────────────────────────────────────┐");
            System.out.println("│ ◆ Wilder Web Browser                                                    - □ x │");
            System.out.println("└───────────────────────────────────────────────────────────────────────────────┘");

            StringBuilder browserNameLine = new StringBuilder();
            StringBuilder browserUnderLine = new StringBuilder();

            if (webSiteNames.size() > 0) {
                browserNameLine.append("│");
                browserUnderLine.append("└");
            }

            for (int i = 0; i < webSiteNames.size(); i++) {
                browserNameLine.append(generateWebSiteName(webSiteNames.get(i)))
                        .append(generateWebSiteIndex(webSiteIndexes.get(i)))
                        .append(" │");
                browserUnderLine.append("───────────────┘");
            }

            System.out.println(browserNameLine);
            System.out.println(browserUnderLine);
        }

        private String generateWebSiteName(final String name) {
            StringBuilder builder = new StringBuilder();

            if (name.length() > 11) {
                return name.substring(0, 11);
            }

            int space = 11 - name.length();
            int interval = space / 2;

            builder.append(SPACE.repeat(interval))
                    .append(name)
                    .append(SPACE.repeat(11 - interval - name.length()));

            return builder.toString();
        }

        private String generateWebSiteIndex(final int index) {
            if (index < 10) {
                return "00" + index;
            }
            if (index < 100) {
                return "0" + index;
            }
            return "" + index;
        }

        public boolean isNotEmpty() {
            return webSiteNames.size() != 0;
        }

        public boolean hasSpace() {
            return webSiteNames.size() < maxWebCount;
        }

        public boolean full() {
            return !hasSpace();
        }

        public synchronized void removeAndNotify() {
            if (isNotEmpty()) {
                System.out.println(webSiteNames.get(0) + " 사이트가 종료됩니다.");
                webSiteNames.remove(0);
                webSiteIndexes.remove(0);
                showRunningBrowser();
                notify();
            }
        }
    }

    static class WebSiteCloser implements Runnable {
        private final WebBrowser webBrowser;

        public WebSiteCloser(WebBrowser webBrowser) {
            this.webBrowser = webBrowser;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Thread.sleep(3000);
                    webBrowser.removeAndNotify();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        final WebBrowser webBrowser = new WebBrowser(5);

        final Thread threadA = new Thread(new WebSite("Google", webBrowser));
        final Thread threadB = new Thread(new WebSite("Naver", webBrowser));
        final Thread threadC = new Thread(new WebSite("Daum", webBrowser));
        final Thread closer = new Thread(new WebSiteCloser(webBrowser));

        closer.start();
        threadA.start();
        threadB.start();
        threadC.start();
    }
}
