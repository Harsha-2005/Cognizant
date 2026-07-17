import './App.css';

function App() {
  return (
    <div>
      <h1>Single Page Application</h1>
      <p>Single Page Application(SPA) is a type of we application that loads a single HTML page and dynamically updates content as the user interacts with the app with out reloading the entire page.</p>
      <p>Frameworks like Angular,Vue.js commonly used to build SPA's</p>
      <h1>React and its working</h1>
      <p>React is a javascript library developed by the facebook formerly called as <strong>Meta</strong>. It is build for creating the interactive and dynamic user interfaces,especially for the Single Page Application(SPA).It mainly focuses on creating the reusable UI components that efficiently update and render when data changes.
        React operates based on the component based architecture and uses a virtual DOM to optimize the performance.</p>
      <h1>Difference between the SPA and MPA</h1>
      <table>
        <thead>
          <tr>
            <th><strong>Single Page Application(SPA)</strong></th>
            <th><strong>Multi Page Application(MPA)</strong></th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>Loads a single HTML page and dynamically updates content using JavaScript.</td>
            <td>Loads multiple HTML pages — each page is fetched from the server separately.</td>
          </tr>
          <tr>
            <td>No full page reloads; only parts of the page update.</td>
            <td>Full page reload occurs whenever the user navigates to a new page.</td>
          </tr>
        </tbody>
      </table>
    </div >
  );
}

export default App;
