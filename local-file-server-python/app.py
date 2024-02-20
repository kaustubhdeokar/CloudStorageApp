from flask import Flask, render_template, request, send_from_directory
import os
from flask_cors import CORS, cross_origin
from flask import jsonify

app = Flask(__name__)

# Define the folder to store uploaded files
UPLOAD_FOLDER = 'uploads'
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

@app.route('/')
def index():
    files = os.listdir(UPLOAD_FOLDER)
    print(list(files))
    return render_template('index.html', files = files)

@app.route('/upload', methods=['POST'])
@cross_origin()
def upload_file():
    if 'file' not in request.files:
        return 'No file part'
    
    file = request.files['file']
    
    if file.filename == '':
        return 'No selected file'

    filename = os.path.join(app.config['UPLOAD_FOLDER'], file.filename)
    file.save(filename)
    return 'File uploaded successfully'

@app.route('/download/<filename>')
@cross_origin()
def download_file(filename):
    return send_from_directory(app.config['UPLOAD_FOLDER'], filename, as_attachment=True)


@app.route('/list', methods = ['GET'])
@cross_origin()
def list_files():
    list = []
    for file in os.listdir(UPLOAD_FOLDER):
        list.append({'name':file})
    tup = tuple(list)
    return jsonify(
        data=tup
    ), 200



if __name__ == '__main__':
    if not os.path.exists(UPLOAD_FOLDER):
        os.makedirs(UPLOAD_FOLDER)
    app.run(debug=True)
