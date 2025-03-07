package egovframework.com.pagination;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import java.text.MessageFormat;

public class EgovPaginationFormat {

    protected String previousPageLabel = "<a onclick=\"{0}({1})\" class=\"page-navi prev\" href=\"#\">이전</a>";
    protected String firstPageLabel = "<a onclick=\"{0}({1})\" class=\"page-link\" href=\"#\">{2}</a>";
    protected String currentPageLabel = "<a class=\"page-link active\" href=\"#\">{0}</a>";
    protected String otherPageLabel = "<a onclick=\"{0}({1})\" class=\"page-link\" href=\"#\">{2}</a>";
    protected String nextPageLabel = "<a onclick=\"{0}({1})\" class=\"page-navi next\" href=\"#\">다음</a>";
    protected String lastPageLabel = "<a onclick=\"{0}({1})\" class=\"page-link\" href=\"#\">{2}</a>";
    protected String dotPageLabel = "<span class=\"page-link link-dot\"></span>";

    public String paginationFormat(PaginationInfo paginationInfo, String jsFunction) {

        if (paginationInfo.getTotalPageCount() == 0) {              // count 조회 결과가 0일 경우
            return "<div class=\"page-links\">" +
                    "<a class=\"page-link active\" href=\"#\">1</a>" +
                    "</div>";
        }

        StringBuffer stringBuffer = new StringBuffer();

        int firstPageNo = paginationInfo.getFirstPageNo();          // 첫 페이지 번호
        int totalPageCount = paginationInfo.getTotalPageCount();    // 전체 페이지 수
        int pageSize = paginationInfo.getPageSize();                // 페이지 리스트 크기
        int currentPageNo = paginationInfo.getCurrentPageNo();      // 현재 페이지 번호

        int halfPageSize = pageSize / 2;                            // 페이지 리스트의 절반 크기 계산
        int startPageNo, endPageNo;

        if (pageSize % 2 == 0) {                                    // pageSize가 짝수일 경우
            startPageNo = currentPageNo - halfPageSize + 1;
            endPageNo = currentPageNo + halfPageSize;
        } else {                                                    // pageSize가 홀수일 경우
            startPageNo = currentPageNo - halfPageSize;
            endPageNo = currentPageNo + halfPageSize;
        }

        if (startPageNo < 1) {                                      // 시작 페이지 번호가 1보다 작을 경우
            startPageNo = 1;
            endPageNo = startPageNo + pageSize - 1;
        }
        if (endPageNo > totalPageCount) {                           // 종료 페이지 번호가 전체 페이지 수보다 클 경우
            endPageNo = totalPageCount;
            startPageNo = endPageNo - pageSize + 1;
            if (startPageNo < 1) {
                startPageNo = 1;
            }
        }

        /* 페이지네이션 렌더링 */

        // 이전 페이지 링크 추가
        if (currentPageNo != firstPageNo) {
            stringBuffer.append(MessageFormat.format(previousPageLabel, new Object[]{jsFunction, Integer.toString(currentPageNo - 1)}));
        }

        stringBuffer.append("<div class=\"page-links\">");

        // 시작 페이지 번호가 1보다 클 경우 첫 페이지 링크 추가
        if (startPageNo > 1) {
            stringBuffer.append(MessageFormat.format(firstPageLabel, new Object[]{jsFunction, Integer.toString(firstPageNo), Integer.toString(firstPageNo)}));
            stringBuffer.append(dotPageLabel);
        }

        // 페이지 번호 링크 추가
        for (int i = startPageNo; i <= endPageNo; i++) {
            if (i == currentPageNo) {
                stringBuffer.append(MessageFormat.format(currentPageLabel, new Object[]{Integer.toString(i)}));
            } else {
                stringBuffer.append(MessageFormat.format(otherPageLabel, new Object[]{jsFunction, Integer.toString(i), Integer.toString(i)}));
            }
        }

        // 종료 페이지 번호가 전체 페이지 수보다 작을 경우 마지막 페이지 링크 추가
        if (endPageNo < totalPageCount) {
            stringBuffer.append(dotPageLabel);
            stringBuffer.append(MessageFormat.format(lastPageLabel, new Object[]{jsFunction, Integer.toString(totalPageCount), Integer.toString(totalPageCount)}));
        }

        stringBuffer.append("</div>");

        // 다음 페이지 링크 추가
        if (currentPageNo != totalPageCount) {
            stringBuffer.append(MessageFormat.format(nextPageLabel, new Object[]{jsFunction, Integer.toString(currentPageNo + 1)}));
        }

        return stringBuffer.toString();
    }
}
