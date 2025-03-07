import {
    AccessibilityHelp,
    Autoformat,
    AutoImage,
    AutoLink,
    Autosave,
    Bold,
    Essentials,
    FileRepository,
    FindAndReplace,
    ImageBlock,
    ImageCaption,
    ImageInline,
    ImageInsert,
    ImageInsertViaUrl,
    ImageResize,
    ImageStyle,
    ImageTextAlternative,
    ImageToolbar,
    ImageUpload,
    Italic,
    Link,
    LinkImage,
    List,
    ListProperties,
    Paragraph,
    SelectAll,
    Table,
    TableCaption,
    TableCellProperties,
    TableColumnResize,
    TableProperties,
    TableToolbar,
    TextTransformation,
    TodoList,
    Undo
} from '/cop/brd/ckeditor5_simple/ckeditor5.js';

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
			console.log(response);
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

export {EditorConfig}
