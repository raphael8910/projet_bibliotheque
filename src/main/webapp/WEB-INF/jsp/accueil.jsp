<script type="text/javascript">
        var gk_isXlsx = false;
        var gk_xlsxFileLookup = {};
        var gk_fileData = {};
        function filledCell(cell) {
          return cell !== '' && cell != null;
        }
        function loadFileData(filename) {
        if (gk_isXlsx && gk_xlsxFileLookup[filename]) {
            try {
                var workbook = XLSX.read(gk_fileData[filename], { type: 'base64' });
                var firstSheetName = workbook.SheetNames[0];
                var worksheet = workbook.Sheets[firstSheetName];

                var jsonData = XLSX.utils.sheet_to_json(worksheet, { header: 1, blankrows: false, defval: '' });
                var filteredData = jsonData.filter(row => row.some(filledCell));

                var headerRowIndex = filteredData.findIndex((row, index) =>
                  row.filter(filledCell).length >= filteredData[index + 1]?.filter(filledCell).length
                );
                if (headerRowIndex === -1 || headerRowIndex > 25) {
                  headerRowIndex = 0;
                }

                var csv = XLSX.utils.aoa_to_sheet(filteredData.slice(headerRowIndex)); // Create a new sheet from filtered array of arrays
                csv = XLSX.utils.sheet_to_csv(csv, { header: 1 });
                return csv;
            } catch (e) {
                console.error(e);
                return "";
            }
        }
        return gk_fileData[filename] || "";
        }
        </script><!DOCTYPE html>
<html>
<head>
    <title>Accueil Bibliothecaire</title>
</head>
<body>
    <h2>Menu Bibliothecaire</h2>
    <ul>
        <li><a href="/pret">Preter un exemplaire</a></li>
        <li><a href="/reservation">Reserver un exemplaire</a></li>
        <li><a href="/prolongation">Prolonger un pret</a></li>
        <li><a href="/adherent/inscrire">Inscrire un adherent</a></li>
        <li><a href="/retour">Retourner un livre</a></li>
    </ul>
</body>
</html>