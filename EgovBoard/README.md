# 게시판

블로그, 커뮤니티, 통합게시판, 방명록 등으로 사용하기 위한 게시판을 만들 수 있는 컴포넌트   
Ckeditor를 이용해 editor 형태로 게시글을 작성하거나 파일첨부, 파일다운로드 등의 기능을 사용해 게시글을 작성할 수 있다.

## 프로젝트 구성

``` text
  EgovBoard
    ├ /src/main
    │   ├ java
    │   │   └ egovframework.com
    │   │       ├ config
    │   │       ├ filter
    │   │       ├ pagination
    │   │       ├ cop
    │   │       │  ├ bbs
    │   │       │  ├ bls
    │   │       │  ├ brd
    │   │       │  └ cmy
    │   │       └ EgovAuthorApplication
    │   │
    │   └ resources
    │       ├ messages.egovframework.com
    │       ├ static
    │       ├ templates.egovframework.com.cop
    │       │   ├ bbs
    │       │   ├ bls
    │       │   ├ brd
    │       │   └ cmy
    │       └ application.yml
    └ pom.xml
```

- 게시판 관리(`/com/cop/bbs`) : 통합게시판, 방명록 등의 게시판을 생성하고 등록된 게시판들에 대하여 관련된 속성정보를 관리
- 블로그 관리(`/com/cop/bls`) : 블로그게시판을 생성하고 등록된 게시판들에 대하여 관련된 속성정보를 관리
- 게시글 관리(`/com/cop/brd`) : 사용자 간의 정보공유를 위해 공통으로 사용되는 게시판을 관리할 수 있도록 게시물을 등록하고 등록된 게시물의 목록을 조회할 수 있는 기능
- 커뮤니티 관리(`/com/cop/cmy`) : 커뮤니티를 생성하고 등록된 커뮤니티들에 대하여 관련된 속성정보를 관리

## 화면 구성

블로그 / 커뮤니티 관리 등록 → 게시판 등록 → 게시글 등록 순으로 등록 가능   

### 1. 블로그 / 커뮤니티 관리

※ 블로그와 커뮤니티의 형태는 같으나 다른 아이디로 구분   

- 블로그의 목록
  ![blog_list](https://github.com/user-attachments/assets/4b05d147-8d8b-479b-a011-0a06c552484d)    
  *이때 블로그/커뮤니티에서 생성된 게시판은 게시판관리의 게시판 목록에는 포함되지 않음

- 등록된 블로그의 정보가 담긴 상세화면
  ![blog_detail](https://github.com/user-attachments/assets/50ba211c-cb1e-4664-aecb-faa3d1cef0e0)     
  - '블로그 주소'로 들어가면 해당 블로그 아이디를 참조하는 게시판을 생성할 수 있다.
  - 블로그 생성 후 참조되는 게시판은 블로그의 메뉴 역할을 함


### 2. 게시판 관리

※ 블로그/커뮤니티 관리의 게시판과 게시판관리의 게시판의 형태는 동일하지만 각 참조하는 아이디가 다르므로 목록을 공유하지는 않는다.   

#### 1) 게시판 목록    
  ![bbs_list](https://github.com/user-attachments/assets/f225ce64-706a-4d99-9b49-10e3732fbcd1)     
  *블로그 / 커뮤니티 게시판의 경우 해당되는 블로그/커뮤니티가 제목에 포함되어있음   
  예시) 블로그에서 등록한 게시판
  ![blog_bbs_list](https://github.com/user-attachments/assets/aa98da7e-ab97-4c56-a7b1-0e38b90203df)   
  
#### 2) 게시판 등록   
  ![bbs_insert](https://github.com/user-attachments/assets/96f42304-03c6-4e6d-8d20-3b87d4468b76)   
  - 게시판 등록 기능
    - 게시판 유형
      - 통합게시판 / 방명록 : 방명록 선택 시 아래 기능은 전부 미포함되고 게시글만 작성할 수 있는 게시판 형태로 생성됨
    - 답장(답글)가능여부
      - 답글 등록시 '화살표' 아이콘으로 표현
      ![bbs_reply](https://github.com/user-attachments/assets/e1e09936-4c12-44da-b58f-bc1be0096a2a)   
      - 답글 미사용 / 방명록 게시판의 경우 게시글의 '답글버튼' 비활성화(disabled)
        
    - 파일첨부 가능 여부 / 첨부파일 가능 숫자   
      ![bbs_fileUpload](https://github.com/user-attachments/assets/6c3a1bb4-55dc-4035-bd3a-b0c71f547951)   
      - 파일 첨부 기능을 추가하는지, 최대 몇 개의 파일까지 첨부할 수 있는지 설정
      - 첨부가능한 파일에 대한 정보는 '게시글 등록'에서 설정함

    - 추가선택사항   
       ※ 게시판 등록시 comtnbbsmaster 테이블에 등록되지만 추가선택사항의 경우 comtnbbsmasteroptn 테이블에 저장   
      - 선택사항으로 처음부터 추가할 필요 없이 추후에 수정해도 되지만 한번이라도 선택 시 추후 수정 불가
      - 댓글(answerAt)
        ![bbs_comment](https://github.com/user-attachments/assets/37c67cfe-ee5f-4cdf-9071-335e16f11ca5)      
      - 만족도조사   
        ![bbs_stsfdg](https://github.com/user-attachments/assets/db64c06c-fb6e-4ed1-8d19-468d99e1aba5)   
        
  #### 3) 게시판 상세
  ![bbs_detail](https://github.com/user-attachments/assets/bb5c416a-d88c-4d31-816d-a7b45ce87aee)   
  '통합게시판' 으로 생성되어 게시판의 모든 특성을 추가한 게시판
  
<br/>

### 3. 게시글 관리

#### 1) 게시글 목록
![brd_insert](https://github.com/user-attachments/assets/cc430d6b-89f0-453f-a6f8-f56635423570)   
- 게시글 등록 시 익명/비밀글/공지 등록 시 목록에서 표시됨
  - 공지의 경우 등록 순서, 페이지 번호에 관계없이 게시글 최상단에 위치
  - 비밀글의 경우 작성자 본인만 상세내역 확인 가능
  - 익명의 경우 등록자이름이 '익명'으로 등록
  - 답글의 경우 '화살표 및 공백'을 이용해 답글 표시
  - 제목 진하게의 경우 제목이 Bold 형태로 처리
 
  <br/>

#### 2) 게시글 등록

- 게시글 및 옵션 설정
  ![brd_optionType](https://github.com/user-attachments/assets/7204edee-f001-4fe9-952e-9369e155aa38)   
  - 공지 / 비밀글 / 익명은 1개만 선택 할 수 있음   
    ![brd_option_check](https://github.com/user-attachments/assets/c5e6a7ac-2bb2-45b6-bd96-ce4938afe5dd)   
    1개 선택 시 나머지 버튼 disabled 처리   
       
  - 제목진하게 설정 시 비밀글 설정 불가
   
- 게시글 내용(Ckeditor)
  ![bbs_editor](https://github.com/user-attachments/assets/a32876e1-0b2d-4cb9-a2df-c9591095b9a1)   
  - Ckeditor5 사용
  - resources/static 하위 ckedtior 항목은 ckeditor에서 제공한 코드
  - js/ckEditor_simple-main.js를 이용해 Ckeditor 설정
      ``` javascript
          const EditorConfig = {
        	toolbar: {
        		items: [
        			'undo', 'redo', '|', 'findAndReplace', '|', 'bold', 'italic', '|', 'link', 'insertImage', 'insertTable', '|', 'bulletedList', 'numberedList', 'todoList'
        		],
        		shouldNotGroupWhenFull: false
        	},
        	plugins: [
        		AccessibilityHelp, Autoformat, AutoImage, AutoLink, Autosave, Bold, Essentials, FindAndReplace,
        		ImageBlock, ImageCaption, ImageInline, ImageInsert, ImageInsertViaUrl, ImageResize, ImageStyle, ImageTextAlternative, ImageToolbar, ImageUpload,
        		Italic, Link, LinkImage, List, ListProperties, Paragraph, SelectAll,
        		Table, TableCaption, TableCellProperties, TableColumnResize, TableProperties, TableToolbar,
        		TextTransformation, TodoList, Undo, FileRepository,
        		MyCustomUploadAdapterPlugin
        	],
        	image: {
        		toolbar: [
        			'toggleImageCaption', 'imageTextAlternative', '|', 'imageStyle:inline', 'imageStyle:wrapText', 'imageStyle:breakText', '|', 'resizeImage'
        		]
        	},
        	// initialData: 초기화 값
        	link: {
        		addTargetToExternalLinks: true,
        		defaultProtocol: 'https://',
        		decorators: {
        			toggleDownloadable: {
        				mode: 'manual',
        				label: 'Downloadable',
        				attributes: {
        					download: 'file'
        				}
        			}
        		}
        	},
        	list: {
        		properties: {
        			styles: true,
        			startIndex: true,
        			reversed: true
        		}
        	},
        	// placeholder: 'Type or paste your content here!',
        	table: {
        		contentToolbar: ['tableColumn', 'tableRow', 'mergeTableCells', 'tableProperties', 'tableCellProperties']
        	},
        	simpleUpload:{
        		uploadUrl: "/cop/brd/ckImageUpload",
        		withCredentials: true,
        	}
        };
      ```
      Ckedtior에서 사용하고자 하는 기능 설정
    
    
      - Ckeditor 이미지 업로드를 돕는 스크립트   
        ``` javascript
            class MyUploadAdapter {
            	constructor( loader ) {
            		this.loader = loader;
            	}
            
            	upload() {
            		return this.loader.file
            			.then( file => new Promise( ( resolve, reject ) => {
            				this._initRequest();
            				this._initListeners(resolve, reject, file);
            				this._sendRequest(file);
            			} ) );
            	}
            
            	abort() {
            		if ( this.xhr ) {
            			this.xhr.abort();
            		}
            	}
            
            	_initRequest() {
            		const xhr = this.xhr = new XMLHttpRequest();
            
            		xhr.open( 'POST', '/cop/brd/ckImageUpload', true );
            		xhr.responseType = 'json';
            	}
            
            	_initListeners( resolve, reject, file ) {
            		const xhr = this.xhr;
            		const loader = this.loader;
            		const genericErrorText = `Couldn't upload file: ${ file.name }.`;
            
            		xhr.addEventListener( 'error', () => reject( genericErrorText ) );
            		xhr.addEventListener( 'abort', () => reject() );
            		xhr.addEventListener( 'load', () => {
            			const response = xhr.response;
            			if ( !response || response.error ) {
            				return reject( response && response.error ? response.error.message : genericErrorText );
            			}
            
            			resolve( {
            				default: response.url
            			} );
            		} );
            
            		if ( xhr.upload ) {
            			xhr.upload.addEventListener( 'progress', evt => {
            				if ( evt.lengthComputable ) {
            					loader.uploadTotal = evt.total;
            					loader.uploaded = evt.loaded;
            				}
            			} );
            		}
            	}
            
            	_sendRequest(file) {
            		const data = new FormData();
            		data.append( 'upload', file);
            		this.xhr.send(data);
            	}
            }
            
            function MyCustomUploadAdapterPlugin( editor ) {
            	editor.plugins.get( 'FileRepository' ).createUploadAdapter = ( loader ) => {
            
            		return new MyUploadAdapter( loader );
            	};
            }
        ```
        기존 Ckedtior FileUpload 코드를 custom 하여 사용
        
        <br/>
        - 스크립트에서 "/cop/brd/ckImageUpload" 로 전송하여 EgovBoardCKEditorController에서 처리
          
          ``` java
              @PostMapping("/cop/brd/ckImageUpload")
              public Map<String, Object> ckImageUpload(MultipartRequest request) throws IOException {
                  Map<String, Object> response = new HashMap<>();
          
                  MultipartFile uploadFile = request.getFile("upload");
          
                  // 파일 Null 검사
                  if (ObjectUtils.isEmpty(uploadFile)) {
                      response.put("uploaded", 0);
                      response.put("error", "No files uploaded.");
                      return response;
                  }
          
                  // 파일 크기 검사 (1MB 초과 시 업로드 불가)
                  if (uploadFile.getSize() > maxFileSize) {
                      response.put("uploaded", 0);
                      response.put("error", "File size exceeds the maximum limit of 1MB.");
                      return response;
                  }
          
                  String newFileName = getFileName(uploadFile);
          
                  // 파일 확장자 검사
                  if (!isValidImageFile(newFileName)) {
                      response.put("uploaded", 0);
                      response.put("error", "Invalid file type.");
                      return response;
                  }
          
                  File destinationFile = new File(uploadDir, newFileName);
                  uploadFile.transferTo(destinationFile);
          
                  String fileUrl = "/cop/brd/ckeditor/" + newFileName;
                  response.put("url", fileUrl);
          
                  return response;
              }
          ```
          
          - 이미지 업로드 시 진행하는 Validation의 설정 값은 application.yml에서 지정하여 사용할 수 있다.   
            확장자 및 업로드 경로, 최대 파일 크기 설정 가능          
            ```yml
              file:
              ck:
                upload-dir: ${user.home}/upload/ckeditor
                allowed-extensions: jpg,jpeg,gif,bmp,png
              max-file-size: 1048576
            ```
        
        
  
- 게시글 날짜
  ![bbs_date](https://github.com/user-attachments/assets/9cfea933-6aba-456b-a930-c26dbadb154e)   
  
- 파일 첨부
  ![bbs_fileUpload](https://github.com/user-attachments/assets/f91ae719-6e23-441e-be4d-c22c0d8b2200)   

  ```yml
  file:
  board:
    upload-dir: ${user.home}/upload/board
    allowed-extensions: jpg,jpeg,gif,bmp,png,pdf
  max-file-size: 1048576
  ```

## 유의사항

게시글 등록/수정 시 EgovSearch에서 사용할 수 있는 데이터를 함께 등록할 수 있다.   
EgovSearch 서비스 이용이 가능한 상태여야하므로 사용 방법은 EgovSerach의 ReadMe를 참조.

## 참조

1. [Ckeditor5](https://ckeditor.com/)
