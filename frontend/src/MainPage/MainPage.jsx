import { React, useEffect, useState } from 'react'

const FileList = ({ data }) => {
  return (
    <div>
      <h2>File List</h2>
      <ul>
        {data.map((item, index) => (
          <li key={index}>{item.name}</li>
        ))}
      </ul>
    </div>
  );
};

function MainPage() {
  const [data, setData] = useState([]);
  const [selectedFile, setSelectedFile] = useState(null);
  const [message, setMessage] = useState('');
  const [fileuploaded, setfileuploaded] = useState(true);

  useEffect(() => {
    // Simulating a fetch request with a delay
    const fetchData = async () => {
      try {
        // Replace this with your actual API endpoint or data fetching logic
        const response = await fetch('http://localhost:5000/list');
        const result = await response.json();
        setData(result.data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };
    fetchData();
    console.log('fetch data');
    if (fileuploaded) {
      setfileuploaded(false);
    }
  }, [fileuploaded]);

  const handleFileChange = async (event) => {
    const newFile = event.target.files[0];
    if (newFile) {
      setSelectedFile(newFile);
      console.log('setting selected file');
    }
  };


  let handleSubmit = async (e) => {

    e.preventDefault();
    const formData = new FormData();
    formData.append('file', selectedFile);
    try {
      let res = await fetch("http://localhost:5000/upload", {
        method: "POST",
        body: formData,
      });
      if (res.status === 200) {
        setMessage("File uploaded successfully.");
        setfileuploaded(true);
      } else {
        setMessage("Some error occured");
      }
    } catch (error) {
      setMessage('Error uploading file:', error);
    }
  };

  return (
    <div>
      <h1>React Form</h1>
      <input type="file" onChange={handleFileChange} />
      <button onClick={handleSubmit}>Add File</button>
      <div className="message">{message ? <p>{message}</p> : null}</div>
      <FileList data={data} />
    </div>

  );
}

export default MainPage