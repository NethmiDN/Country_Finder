import { useState, useEffect } from 'react';
import './App.css';

interface Country {
  name: string;
  capital: string;
  region: string;
  population: number;
  flag: string;
}

function App() {
  const [countries, setCountries] = useState<Country[]>([]);
  const [search, setSearch] = useState('');
  const [selectedCountry, setSelectedCountry] = useState<Country | null>(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchCountries();
  }, []);

  const fetchCountries = async (query: string = '') => {
    setLoading(true);
    try {
      const url = query ? `http://localhost:8080/api/countries?search=${encodeURIComponent(query)}` : `http://localhost:8080/api/countries`;
      const response = await fetch(url);
      if (response.ok) {
        const data = await response.json();
        setCountries(data);
      } else {
        console.error('Failed to fetch countries');
      }
    } catch (e) {
      console.error(e);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    const timer = setTimeout(() => {
      fetchCountries(search);
    }, 300);

    return () => clearTimeout(timer);
  }, [search]);

  return (
    <div className="container">
      <h1>Countries</h1>
      
      <div className="search-bar">
        <input 
          type="text" 
          placeholder="Search countries..." 
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>

      {loading ? (
        <p>Loading...</p>
      ) : (
        <table className="countries-table">
          <thead>
            <tr>
              <th>Flag</th>
              <th>Name</th>
              <th>Capital</th>
              <th>Region</th>
              <th>Population</th>
            </tr>
          </thead>
          <tbody>
            {countries.map((country, index) => (
              <tr key={index} onClick={() => setSelectedCountry(country)}>
                <td><img src={country.flag} alt={`${country.name} flag`} width="40" style={{ border: '1px solid #ccc' }} /></td>
                <td>{country.name}</td>
                <td>{country.capital}</td>
                <td>{country.region}</td>
                <td>{country.population.toLocaleString()}</td>
              </tr>
            ))}
            {countries.length === 0 && (
              <tr>
                <td colSpan={5} style={{textAlign: 'center'}}>No countries found</td>
              </tr>
            )}
          </tbody>
        </table>
      )}

      {selectedCountry && (
        <div className="modal-overlay" onClick={() => setSelectedCountry(null)}>
          <div className="modal-content" onClick={e => e.stopPropagation()}>
            <button className="close-btn" onClick={() => setSelectedCountry(null)}>X</button>
            <h2 style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
              {selectedCountry.name} 
              <img src={selectedCountry.flag} alt="flag" width="40" style={{ border: '1px solid #ccc' }} />
            </h2>
            <p><strong>Capital:</strong> {selectedCountry.capital || 'N/A'}</p>
            <p><strong>Region:</strong> {selectedCountry.region || 'N/A'}</p>
            <p><strong>Population:</strong> {selectedCountry.population.toLocaleString() || 'N/A'}</p>
          </div>
        </div>
      )}
    </div>
  );
}

export default App;
